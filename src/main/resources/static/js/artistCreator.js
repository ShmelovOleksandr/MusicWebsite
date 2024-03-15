const nameErrorField = document.getElementById("nameError");
const dateErrorField = document.getElementById("dateError");
const listenersErrorField = document.getElementById("listenersError");
const generalErrorField = document.getElementById("listenersError");

const nameFieldName = "name";
const dateFieldName = "birthDate";
const listenersFieldName = "listeners";

function addEventListeners() {
    document.getElementById('submit').addEventListener('click', submitForm);
}

async function submitForm(event) {
    event.preventDefault()

    const response = await sendPostRequest();
    if(response.status === 201) {
        window.location.replace("/artists")
    } else if (response.status === 400) {
        const errorJson = await response.json()
        for (let i = 0; i < errorJson.errors.length; i++) {
            const error = errorJson.errors[i];
            let errorField = null;
            console.log(error)
            switch (error.field) {
                case nameFieldName:
                    errorField = nameErrorField;
                    break;
                case dateFieldName:
                    errorField = dateErrorField;
                    break;
                case listenersFieldName:
                    errorField = listenersErrorField;
                    break;
                default:
                    errorField = generalErrorField
            }

            errorField.innerText = error.defaultMessage;
            errorField.classList.remove("disabled");
        }
    } else {
        generalErrorField.innerText = "An unknown error has occurred. Try one more time.";
        generalErrorField.classList.remove("disabled");
    }
}

async function sendPostRequest() {
    const formData = {
        name: document.getElementById('name').value,
        birthDate: document.getElementById('date').value,
        listeners: document.getElementById('listeners').value
    };

    return await fetch('/api/artists', {
        method: 'POST',
        headers: {
            'Content-Type': 'application/json'
        },
        body: JSON.stringify(formData)
    })
}

addEventListeners()
