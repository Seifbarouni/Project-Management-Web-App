if (document.title == "🔒 Register") {
    const userName = document.getElementById('Username')
    const password = document.getElementById('password')
    const form = document.getElementById('form')
    const errorElement = document.getElementById('error')
    form.addEventListener('submit', e => {
        let messages = []
        if (userName.value === '' || userName.value == null) {
            messages.push('❌ Username is required')
        }

        if (userName.value.length < 2) {
            messages.push('❌ Username must be longer than 2 characters')
        }

        if (password.value.length <= 8) {
            messages.push('🔑 Password must be longer than 8 characters')
        }

        if (password.value.length >= 20) {
            messages.push('🔑 Password must be less than 20 characters')
        }

        if (password.value === 'password' || password.value === 'password123') {
            messages.push('🔑 weak password')
        }

        if (messages.length > 0) {
            e.preventDefault()
            errorElement.innerText = messages.join('\n ')
        }
    })
} else if (document.title == "🔒 Login") {
    const userName = document.getElementById('Username')
    const password = document.getElementById('password')
    const form = document.getElementById('login_form')
    const errorElement = document.getElementById('login_error')

    form.addEventListener('submit', e => {
        let messages = []
        if (userName.value === '' || userName.value == null) {
            messages.push('❌ Username is required')
        }

        if (userName.value.length < 2) {
            messages.push('❌ Username must be longer than 2 characters')
        }

        if (password.value.length < 4) {
            messages.push('🔑 Short password')
        }
        if (messages.length > 0) {
            e.preventDefault()
            messages.push("⚠️ Register if you don't have an account")
            errorElement.innerText = messages.join('\n ')
        }
    })


} else {
    const addUserUserName = document.getElementById('username')
    const addUserForm = document.getElementById('add_user_form')
    const addUserErrorElement = document.getElementById('add_user_error')


    addUserForm.addEventListener('submit', e => {
        let addUsermessages = []
        if (addUserUserName.value === '' || addUserUserName.value == null) {
            addUsermessages.push('❌ Username is required')
        }

        if (addUserUserName.value.length < 2) {
            addUsermessages.push('❌ Username must be longer than 2 characters')
        }
        if (addUsermessages.length > 0) {
            e.preventDefault()
            addUserErrorElement.innerText = addUsermessages.join('\n ')
        }

    })
}

