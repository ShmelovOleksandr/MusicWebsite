// import '../scss/search-issues.scss'
import 'bootstrap'

import { header, token } from './util/csrf.js'

const submitButton  = document.getElementById('submit')
const usernameInput = document.getElementById('username')
const passwordInput = document.getElementById('password')
const passwordValidationInput = document.getElementById('password_validation')

const errorsField = document.getElementById('errorsField')

submitButton.addEventListener('click', submitForm)

async function submitForm(event) {
    event.preventDefault() // Prevent form submission

    const username = usernameInput.value
    const password = passwordInput.value
    const passwordValidation = passwordValidationInput.value

    // Basic validation
    if (username.trim() === '') {
        alert('Please enter a username.')
        return
    }

    if (password.trim() === '') {
        alert('Please enter a password.')
        return
    }

    if (password !== passwordValidation) {
        alert('Passwords do not match.')
        return
    }

    const response = await sendPostRequest(username, password)
    console.log(response.status)
    if (response.ok)
        window.location.replace('/')
    else
        errorsField.innerText = 'Couldn\'t register, try one more time.'

}

async function sendPostRequest(username, password) {
    const formData = {
        username: username,
        password: password
    }

    return await fetch('/register', {
        method: 'POST',
        headers: {
            'Content-Type': 'application/json',
            [header]: token
        },
        body: JSON.stringify(formData)
    })
}
