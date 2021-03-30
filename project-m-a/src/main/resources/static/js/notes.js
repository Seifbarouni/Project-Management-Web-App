const form = document.getElementById("note-form")
const userId = document.body.id
const deleteButons = document.querySelectorAll(".del")
const saveButons = document.querySelectorAll(".save")
const spinner = document.getElementById("spinner")


form.addEventListener("submit", e => {
    e.preventDefault()
    spinner.style.visibility = "visible"
    const title = form.inpt.value
    const pg = form.pg.value
    let yes_or_no
    const tab = document.getElementsByName("yes-or-no")
    for (let i = 0; i < tab.length; i++) {
        if (tab[i].checked) {
            yes_or_no = tab[i].value
        }
    }

    const current_datetime = new Date()
    let formatted_date = current_datetime.getFullYear() + "-" + (current_datetime.getMonth() + 1) + "-" + current_datetime.getDate() + " " + current_datetime.getHours() + ":" + current_datetime.getMinutes() + ":" + current_datetime.getSeconds()

    let payload = {
        userId: userId,
        title: title,
        description: pg,
        important: yes_or_no,
        createdAt: formatted_date,
    }

    async function AddNote() {
        let res = await fetch("http://localhost:9002/notes/addNote", {
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
    AddNote().then(data => {
        if (data != "Success" && data != "") {
            alert(data)
        }
    })
    form.inpt.value = ""
    form.pg.value = ""
    payload = null
    setTimeout(() => {
        location.reload()
    }, 1000)

})

saveButons.forEach(saveButon => {
    saveButon.addEventListener("click", e => {
        spinner.style.visibility = "visible"
        const title = saveButon.parentElement.parentNode.children[2].value
        const description = saveButon.parentElement.parentNode.children[4].value
        const createdAt = saveButon.parentElement.parentNode.children[1].innerHTML.slice(14)
        const important = saveButon.parentElement.parentNode.children[5].innerHTML

        let payload = {
            id: saveButon.id,
            userId: userId,
            title: title,
            description: description,
            important: important,
            createdAt: createdAt,
        }
        async function UpdateNote() {
            let res = await fetch("http://localhost:9002/notes/updateNote", {
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
        UpdateNote().then(data => {
            if (data != "Success" && data != "") {
                alert(data)
            }
        })
        setTimeout(() => {
            location.reload()
        }, 1000)
    })
})

deleteButons.forEach(delButton => {
    delButton.addEventListener("click", e => {
        e.preventDefault()
        spinner.style.visibility = "visible"
        async function DeleteNote() {
            let res = await fetch(`http://localhost:9002/notes/deleteNote/${delButton.id}`, {
                method: "DELETE",
            })
            if (res.ok) {
                let txt = await res.text()
                return txt
            } else {
                return `HTTP error: ${res.status}`
            }
        }
        DeleteNote().then(data => {
            if (data != "Success" && data != "") {
                alert(data)
            }
        })
        setTimeout(() => {
            location.reload()
        }, 1000)
    })
})