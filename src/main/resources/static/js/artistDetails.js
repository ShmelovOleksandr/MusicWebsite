const editButton= document.getElementById("editButton");
const deleteButton = document.getElementById("deleteButton");
const header = document.getElementById("header");
const title = document.getElementById("title");
const artistNameField = document.getElementById("artistNameField");
const artistBirthdateField = document.getElementById("artistBirthDateField");
const artistListenersField = document.getElementById("artistListenersField");
const url = window.location.href
const artistId = url.substring(url.lastIndexOf("/") + 1);

function redirectToArtistEditor() {
    window.location.replace(`/artists/${artistId}/editor`)
}

async function deleteArtist() {
    const response = await fetch("/api/artists/" + artistId, {
        method:"DELETE"
    })
    console.log(response.status)
    if(response.status === 204) {
        window.location.replace("/artists")
    }
}

function updateTextFields(artist) {
    // header.innerText = artist.name;
    // window.location.head.title = artist.name
    title.innerText = artist.name;
}

function updateArtistDetails(artist) {
    artistNameField.innerText = artist.name;
    artistBirthdateField.innerText = artist.birthDate;
    artistListenersField.innerText = artist.listeners
}

function addEventListeners() {
    editButton.addEventListener("click", redirectToArtistEditor);
    deleteButton.addEventListener("click", deleteArtist);
}

async function updateArtistDetailsPage() {
    // const artist = await fetchArtistJson();
    const artistJson = await fetch("/api/artists/" + artistId);
    const artist = await artistJson.json();
    updateArtistDetails(artist)
    updateTextFields(artist)
}

addEventListeners()
updateArtistDetailsPage()

