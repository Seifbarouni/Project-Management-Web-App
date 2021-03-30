package app.projectma.Controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.view.RedirectView;
import org.springframework.security.core.context.SecurityContextHolder;

import java.util.ArrayList;
import java.util.List;

import app.projectma.Models.InputModel;
import app.projectma.Models.MessageModel;
import app.projectma.Models.MyUserDetails;
import app.projectma.Models.Note;
import app.projectma.Models.Project;
import app.projectma.Models.TaskModel;
import app.projectma.Models.User;
import app.projectma.Services.UserService;

@Controller
public class UserController {

    @Autowired
    private UserService userService;
    @Autowired
    private PasswordEncoder passwordEncoder;
    @Autowired
    private RestTemplate restTemplate;

    @GetMapping("/login")
    public String login() {
        return "login";
    }

    @GetMapping("/home")
    public String getLandingPage() {
        // Check if the user is logged in or not
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if (!(auth instanceof AnonymousAuthenticationToken)) {
            return "redirect:/myprojects";
        }
        return "landingpage";
    }

    @GetMapping("/register")
    public String register(Model model, @RequestParam(value = "message", required = false) String message) {
        model.addAttribute("User", new User());
        if (message != null) {
            model.addAttribute("message", message);
        }
        return "register";
    }

    @PostMapping("/register")
    public ModelAndView registerProcess(@ModelAttribute User user) {
        ModelAndView mv = new ModelAndView("redirect:/register");
        if (user != null) {
            user.setPassword(passwordEncoder.encode(user.getPassword()));
            String resultMessage = userService.addUser(user);
            if (resultMessage == "") {
                mv.addObject("message", "success!");
                return mv;
            } else {
                mv.addObject("message", resultMessage);
                return mv;
            }
        }
        mv.addObject("message", "Invalid User");
        return mv;
    }

    @GetMapping("/myprojects")
    public String myprojects(@AuthenticationPrincipal MyUserDetails userDetails, Model model) {
        try {
            ResponseEntity<List<Project>> res = restTemplate.exchange(
                    "http://localhost:9000/data/" + userDetails.getId(), HttpMethod.GET, null,
                    new ParameterizedTypeReference<List<Project>>() {
                    });
            if (res.getBody().isEmpty()) {
                model.addAttribute("projects", "empty");

            } else {
                List<Project> prs = res.getBody();
                for (Project p : prs) {
                    ResponseEntity<Long> numberOfCollaborators = restTemplate.exchange(
                            "http://localhost:9000/data/getNumber/" + p.getId(), HttpMethod.GET, null,
                            new ParameterizedTypeReference<Long>() {
                            });
                    if (numberOfCollaborators.hasBody()) {
                        if (numberOfCollaborators.getBody() == 0L) {
                            p.setNumberOfCollaborators(0L);
                        } else {
                            p.setNumberOfCollaborators(numberOfCollaborators.getBody() - 1);
                        }
                    }
                }
                model.addAttribute("projects", prs);
            }
        } catch (Exception e) {
            return "error";
        }
        model.addAttribute("username", userDetails.getUsername());
        model.addAttribute("id", userDetails.getId());

        return "myprojects";
    }

    @GetMapping("/addproject")
    public String addproject(Model model, @RequestParam(value = "message", required = false) String message) {
        if (message != null) {
            model.addAttribute("message", message);
        }

        List<String> values = new ArrayList<String>();
        values.add("Programming");
        values.add("School");
        values.add("Business");
        values.add("Personal");

        model.addAttribute("Project", new Project());
        model.addAttribute("values", values);

        return "addproject";
    }

    @PostMapping("/addproject")
    public ModelAndView addprojectprocess(@ModelAttribute Project pr,
            @AuthenticationPrincipal MyUserDetails userDetails) {
        ModelAndView mv = new ModelAndView("redirect:/addproject");
        pr.setAdminUsername(userDetails.getUsername());

        try {
            ResponseEntity<String> response = restTemplate
                    .postForEntity("http://localhost:9000/data/" + userDetails.getId(), pr, String.class);
            if (response.getStatusCode() == HttpStatus.OK) {
                mv.setViewName("redirect:/myprojects");
                return mv;
            } else {
                mv.addObject("message", "Error😐,please try again...");
                return mv;
            }
        } catch (Exception e) {
            mv.addObject("message", "Error😐, please try again");
            return mv;
        }
    }

    @GetMapping("/delete/{id}/{title}")
    public ModelAndView DangerZone(@PathVariable(name = "id") Long id, @PathVariable(name = "title") String title,
            ModelMap model, @AuthenticationPrincipal MyUserDetails userDetails) {
        ModelAndView mv = new ModelAndView("dangerzone");

        try {
            ResponseEntity<Project> res = restTemplate.exchange("http://localhost:9000/data/project/" + id,
                    HttpMethod.GET, null, new ParameterizedTypeReference<Project>() {
                    });
            if (res.getBody() == null) {
                mv.addObject("error", "The project (" + title + ")with id=" + id + " does not exist");
                return mv;
            } else if (!res.getBody().getAdminUsername().equals(userDetails.getUsername())) {
                mv.addObject("error", "You are not the admin of the project");
                return mv;
            } else {

                if (res.getBody().getTitle().equals(title)) {
                    mv.addObject("id", id);
                    mv.addObject("title", title);
                    mv.addObject("error", "null");
                    return mv;
                }
                mv.addObject("error", "The project (" + title + ")with id=" + id + " does not exist");
                return mv;

            }
        } catch (Exception e) {
            mv.addObject("error", "ERROR");
            return mv;
        }
    }

    @GetMapping("/addUser/{projectId}/{title}")
    public ModelAndView addUser(@PathVariable(name = "projectId") Long projectId,
            @PathVariable(name = "title") String title, @AuthenticationPrincipal MyUserDetails userDetails,
            ModelMap model) {
        ModelAndView mv = new ModelAndView("addUser");
        try {
            ResponseEntity<Project> res = restTemplate.exchange("http://localhost:9000/data/project/" + projectId,
                    HttpMethod.GET, null, new ParameterizedTypeReference<Project>() {
                    });
            if (res.getBody() == null) {
                mv.addObject("error", "The project (" + title + ")with id=" + projectId + " does not exist");
                return mv;

            } else if (!res.getBody().getAdminUsername().equals(userDetails.getUsername())) {
                mv.addObject("error", "You are not the admin of the project");
                return mv;
            } else {
                if (res.getBody().getTitle().equals(title)) {
                    mv.addObject("id", projectId);
                    mv.addObject("title", title);
                    mv.addObject("input", new InputModel());
                    mv.addObject("error", "null");
                    return mv;
                }
                mv.addObject("error", "The project (" + title + ")with id=" + projectId + " does not exist");
                return mv;
            }
        } catch (Exception e) {
            mv.addObject("error", "ERROR");
            return mv;
        }
    }

    @PostMapping("/addUser/{id}")
    public RedirectView addUserProcess(@ModelAttribute InputModel user,
            @AuthenticationPrincipal MyUserDetails userDetails, @PathVariable(name = "id") Long projectId) {
        Long userId = userService.Userexists(user.getText());
        if (userId != 0L && !user.getText().equals(userDetails.getUsername())) {
            restTemplate.postForEntity("http://localhost:9000/data/addUserToProject/" + userId + "/" + projectId, null,
                    Void.class);
        }
        return new RedirectView("/myprojects");
    }

    @PostMapping("/delete/{id}")
    public RedirectView deleteProject(@PathVariable(name = "id") Long id) {
        if (id != 0) {
            restTemplate.delete("http://localhost:9000/data/" + id);
        }
        return new RedirectView("/myprojects");
    }

    @GetMapping("/")
    public RedirectView redirect() {
        return new RedirectView("/home");
    }

    @GetMapping("/openProject/{projectId}")
    public String openProject(@PathVariable(name = "projectId") Long projectId,
            @AuthenticationPrincipal MyUserDetails userDetails, Model model) {
        Long userId = userDetails.getId();
        ResponseEntity<Boolean> exists = restTemplate.exchange(
                "http://localhost:9000/data/prwithusr/" + projectId + "/" + userId, HttpMethod.GET, null,
                new ParameterizedTypeReference<Boolean>() {
                });
        if (exists.getBody() == true) {
            ResponseEntity<Project> project = restTemplate.exchange("http://localhost:9000/data/project/" + projectId,
                    HttpMethod.GET, null, new ParameterizedTypeReference<Project>() {
                    });
            ResponseEntity<List<Long>> userIds = restTemplate.exchange(
                    "http://localhost:9000/data/getcollaborators/" + projectId + "/" + userId, HttpMethod.GET, null,
                    new ParameterizedTypeReference<List<Long>>() {
                    });
            List<String> collaborators = userService.getCollaboratorsById(userIds.getBody());
            model.addAttribute("username", userDetails.getUsername());
            if (collaborators != null) {
                model.addAttribute("collaborators", collaborators);
            } else {
                model.addAttribute("collaborators", "no-col");
            }
            try {
                ResponseEntity<List<TaskModel>> res = restTemplate.exchange("http://localhost:9001/tasks/" + projectId,
                        HttpMethod.GET, null, new ParameterizedTypeReference<List<TaskModel>>() {
                        });

                if (res.getBody() != null) {
                    model.addAttribute("tasks", res.getBody());
                    model.addAttribute("id", userId);
                    model.addAttribute("username", userDetails.getUsername());
                    model.addAttribute("error", "null");
                    model.addAttribute("projectId", projectId);
                    model.addAttribute("title", project.getBody().getTitle());
                    model.addAttribute("admin", project.getBody().getAdminUsername());
                    model.addAttribute("type", project.getBody().getType());
                } else {
                    model.addAttribute("tasks", "empty");
                    model.addAttribute("id", userId);
                    model.addAttribute("username", userDetails.getUsername());
                    model.addAttribute("error", "null");
                    model.addAttribute("projectId", projectId);
                    model.addAttribute("title", project.getBody().getTitle());
                    model.addAttribute("admin", project.getBody().getAdminUsername());
                    model.addAttribute("type", project.getBody().getType());
                }

            } catch (Exception e) {
                model.addAttribute("error", "Cannot get tasks");
                model.addAttribute("id", userId);
                model.addAttribute("username", userDetails.getUsername());
                model.addAttribute("title", project.getBody().getTitle());
                model.addAttribute("admin", project.getBody().getAdminUsername());
                model.addAttribute("type", project.getBody().getType());
            }

            try {
                ResponseEntity<List<MessageModel>> res = restTemplate.exchange(
                        "http://localhost:9003/messages/getMessages/" + projectId, HttpMethod.GET, null,
                        new ParameterizedTypeReference<List<MessageModel>>() {
                        });
                if (!res.getBody().isEmpty()) {
                    model.addAttribute("messages", res.getBody());
                } else {
                    model.addAttribute("messages", "empty");
                }
            } catch (Exception e) {
                model.addAttribute("messages", "empty");
            }
        } else {
            model.addAttribute("error", "NA");
        }

        return "project";
    }

    @GetMapping("/myprofile")
    public String displayProfile(@AuthenticationPrincipal MyUserDetails userDetails, Model model) {
        model.addAttribute("username", userDetails.getUsername());
        User us = userService.getUserByUsername(userDetails.getUsername());
        model.addAttribute("email", us.getEmail());
        try {
            ResponseEntity<Long> numberOfProjects = restTemplate.exchange(
                    "http://localhost:9000/data/getprojectsnum/" + userDetails.getId(), HttpMethod.GET, null,
                    new ParameterizedTypeReference<Long>() {
                    });
            if (numberOfProjects.hasBody()) {
                model.addAttribute("number", numberOfProjects.getBody());
            }
        } catch (Exception e) {
            model.addAttribute("number", 0);
        }
        return "userprofile";

    }

    @GetMapping("/privacy")
    public String privacyPolicy() {
        return "privacypolicy";
    }

    @GetMapping("/personalNotes")
    public String personalNotes(@AuthenticationPrincipal MyUserDetails userDetails, Model model) {

        try {
            ResponseEntity<List<Note>> notes = restTemplate.exchange(
                    "http://localhost:9002/notes/getNotes/" + userDetails.getId(), HttpMethod.GET, null,
                    new ParameterizedTypeReference<List<Note>>() {
                    });
            if (!notes.getBody().isEmpty()) {
                model.addAttribute("notes", notes.getBody());
            } else {
                model.addAttribute("notes", "null");
            }
        } catch (Exception e) {
            model.addAttribute("notes", "null");
        }

        model.addAttribute("id", userDetails.getId());
        return "personalnotes";
    }
}
