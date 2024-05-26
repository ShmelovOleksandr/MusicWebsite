import 'bootstrap'
import Joi from "joi";

import {header, token} from './util/csrf.js'

const submitButton = document.getElementById('submit')
const backButton = document.getElementById('backButton')

const nameInputField = document.getElementById('name')
const dateInputField = document.getElementById('date')
const listenersInputField = document.getElementById('listeners')

const nameErrorField = document.getElementById('nameError')
const dateErrorField = document.getElementById('dateError')
const listenersErrorField = document.getElementById('listenersError')
const generalErrorField = document.getElementById('listenersError')

const nameFieldName = 'name'
const dateFieldName = 'birthDate'
const listenersFieldName = 'listeners'

const url = window.location.href
const artistId = url.substring(url.lastIndexOf('/artists') + '/artists'.length + 1, url.lastIndexOf('/'))

const artistEditSchema = Joi.object({
    name: Joi.string().min(3).max(18).required(),
    birthDate: Joi.date().less("now").required(),
    listeners: Joi.number().min(0).max(Number.MAX_SAFE_INTEGER).required(),
});

function validateForm(formData) {
    return artistEditSchema.validate(formData);
}

async function trySubmitForm(event) {
    event.preventDefault()
    const formData = {
        name: nameInputField.value,
        birthDate: dateInputField.value,
        listeners: listenersInputField.value
    }
    const error = validateForm(formData);
    if (error) {
        for (let i = 0; i < error.details.length; i++) {
            const errorDetail = error.details[i]
            let errorField = null

            switch (errorDetail.message.substring(1, errorDetail.message.indexOf('"', 2))) {
                case nameFieldName:
                    errorField = nameErrorField
                    break
                case dateFieldName:
                    errorField = dateErrorField
                    break
                case listenersFieldName:
                    errorField = listenersErrorField
                    break
                default:
                    errorField = generalErrorField
            }

            errorField.innerText = errorDetail.message
            errorField.classList.remove('disabled')
        }
    } else {
        await submitForm(formData);
    }
}

async function submitForm(data) {
    const response = await sendPatchRequest(data)
    await handleResponse(response)
}

async function handleResponse(response) {
    if (response.status === 200) {
        window.location.replace(`/artists/${artistId}`)
    } else if (response.status === 400) {
        const errorJson = await response.json()
        for (let i = 0; i < errorJson.errors.length; i++) {
            const error = errorJson.errors[i]
            let errorField = null

            switch (error.field) {
                case nameFieldName:
                    errorField = nameErrorField
                    break
                case dateFieldName:
                    errorField = dateErrorField
                    break
                case listenersFieldName:
                    errorField = listenersErrorField
                    break
                default:
                    errorField = generalErrorField
            }

            errorField.innerText = error.defaultMessage
            errorField.classList.remove('disabled')
        }
    } else {
        generalErrorField.innerText = 'An unknown error has occurred. Try one more time.'
        generalErrorField.classList.remove('disabled')
    }
}

async function sendPatchRequest(data) {
    return await fetch(`/api/artists/${artistId}`, {
        method: 'PATCH',
        headers: {
            'Content-Type': 'application/json',
            [header]: token
        },
        body: JSON.stringify(data)
    })
}

function fetchArtist() {
    return fetch(`/api/artists/${artistId}`).then(response => response.json())
}


async function loadCurrentValues() {
    const artist = await fetchArtist()
    nameInputField.value = artist.name
    dateInputField.value = artist.birthDate
    listenersInputField.value = artist.listeners
}

function addEventListeners() {
    submitButton.addEventListener('click', trySubmitForm)
    backButton.addEventListener('click', (event) => window.location.replace(`/artists/${artistId}`))
}

addEventListeners()
loadCurrentValues()