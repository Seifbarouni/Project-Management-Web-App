let edits = document.querySelectorAll(".edt")
let deletes = document.querySelectorAll(".del")
let forms = document.querySelectorAll(".form")
let edit_project_title = document.getElementById("edit-project-title")





forms.forEach(form => {
    form.addEventListener('submit', e => {
        e.preventDefault()
        if (form.input.value != "") {
            loading.style.visibility = "visible"
            const targetContainer = document.getElementById(`${form.parentNode.children[1].id}`)
            var newCard = document.createElement("div")
            newCard.setAttribute("class", "bg-white p-2 text-sm rounded mt-1 border-b border-grey cursor-pointer hover:bg-gray-100 draggable")
            newCard.setAttribute("draggable", true)
            newCard.innerHTML = `<div class="flex">
            <span class="flex-1">${form.input.value}</span>
            <svg xmlns="http://www.w3.org/2000/svg" viewBox="0 0 20 20" fill="currentColor"
        height="18" width="18"
        class="hover:cursor-pointer"
        >
            <path d="M6 10a2 2 0 11-4 0 2 2 0 014 0zM12 10a2 2 0 11-4 0 2 2 0 014 0zM16 12a2 2 0 100-4 2 2 0 000 4z" />
          </svg>
        </div>`
            targetContainer.appendChild(newCard)
            updateEventListener()
            console.log(form.input.value)
            // POST REQUEST
            let payload = {
                projectId: document.getElementsByTagName("main")[0].id,
                description: form.input.value,
                isTask: false,
                isInProgress: false,
                isInReview: false,
                isDone: false,
            }


            switch (targetContainer.id) {
                case "tasks":
                    payload.isTask = true;
                    break;
                case "In progress":
                    payload.isInProgress = true;
                    break;
                case "In review":
                    payload.isInReview = true;
                    break;
                case "Done":
                    payload.isDone = true;
                    break;

                default:
                    break;
            }

            async function AddData() {
                let res = await fetch("http://localhost:9001/tasks/", {
                    method: "POST",
                    headers: {
                        'Content-type': 'application/json',
                    },
                    body: JSON.stringify(payload),
                })
                if (res.ok) {
                    let txt = await res.text()
                    return txt
                } else {
                    return `HTTP error: ${res.status}`
                }
            }
            AddData().then(data => {
                if (data != "Success" && data != "") {
                    alert(data)
                }
            })
            form.input.value = ""
            payload = null
            setTimeout(() => {
                location.reload()
            }, 1000)

        }
    })
})

deletes.forEach(del => {
    del.addEventListener("click", e => {
        del.parentElement.parentNode.parentElement.className = "hidden"
        del.parentElement.parentElement.children[0].innerHTML = ""
        loading.style.visibility = "visible"



        async function DeleteTask() {
            let res = await fetch(`http://localhost:9001/tasks/${del.id}`, {
                method: 'DELETE',
            })
            if (res.ok) {
                let txt = await res.text()
                return txt
            } else {
                return `HTTP error: ${res.status}`
            }
        }

        DeleteTask().then(data => {
            if (data != "Success" && data != "") {
                alert(data)
            }
        })
        setTimeout(() => {
            location.reload()
        }, 1000)
    })
})


edits.forEach(edt => {
    edt.addEventListener('submit', e => {
        e.preventDefault()
        if (edt.inpt.value != "") {
            loading.style.visibility = "visible"
            let payload = {
                id: edt.id,
                projectId: document.getElementsByTagName("main")[0].id,
                description: edt.inpt.value,
                isTask: false,
                isInProgress: false,
                isInReview: false,
                isDone: false,
            }
            let parent = edt.parentElement.parentElement.parentElement.parentElement.id
            switch (parent) {
                case "edit-task":
                    payload.isTask = true;
                    break;
                case "edit-inprogress":
                    payload.isInProgress = true;
                    break;
                case "edit-inreview":
                    payload.isInReview = true;
                    break;
                case "edit-done":
                    payload.isDone = true;
                    break;

                default:
                    break;
            }

            // PUT REQUEST
            async function UpdateTask() {
                let res = await fetch(`http://localhost:9001/tasks/${edt.id}`, {
                    method: "PUT",
                    headers: {
                        'Content-type': 'application/json',
                    },
                    body: JSON.stringify(payload),
                })
                if (res.ok) {
                    let txt = await res.text()
                    return txt
                } else {
                    return `HTTP error: ${res.status}`
                }
            }

            UpdateTask().then(data => {
                if (data != "Success" && data != "") {
                    alert(data)
                }
            })
            payload = null
            setTimeout(() => {
                location.reload()
            }, 1000)
        }
    })
})

edit_project_title.addEventListener('submit', e => {
    e.preventDefault()
    if (edit_project_title.edit_input.value != "") {
        loading.style.visibility = "visible"
        let payload = {
            id: document.getElementsByTagName("main")[0].id,
            title: edit_project_title.edit_input.value
        }

        async function UpdateTitle() {
            let res = await fetch("http://localhost:9000/data/updateTitle", {
                method: "PUT",
                headers: {
                    'Content-type': 'application/json',
                },
                body: JSON.stringify(payload),
            })
            if (res.ok) {
                let txt = await res.text()
                return txt
            } else {
                return `HTTP error: ${res.status}`
            }
        }
        UpdateTitle().then(data => {
            if (data != "Success" && data != "") {
                alert(data)
            }
        })

        payload = null
        setTimeout(() => {
            location.reload()
        }, 1000)


    }

})
