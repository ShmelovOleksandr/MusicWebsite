import 'bootstrap'
import joi from "joi";
import axios from "axios";

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

const artistEditSchema = joi.object({
    name: joi.string().min(3).max(18).required(),
    birthDate: joi.date().less("now").required(),
    listeners: joi.number().min(0).max(Number.MAX_SAFE_INTEGER).required(),
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
    try {
        return await axios.patch(`/api/artists/${artistId}`, data, {
            headers: {
                'Content-Type': 'application/json',
                [header]: token
            }
        });
    } catch (error) {
        console.error("An error occurred while sending the PATCH request:", error);
    }
}

async function fetchArtist() {
    try {
        const response = await axios.get(`/api/artists/${artistId}`);
        return response.data;
    } catch (error) {
        console.error("An error occurred while fetching the artist:", error);
    }
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