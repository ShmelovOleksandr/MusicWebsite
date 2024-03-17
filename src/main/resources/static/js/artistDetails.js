const editButton= document.getElementById("editButton");
const deleteButton = document.getElementById("deleteButton");
const header = document.getElementById("header");
const title = document.getElementById("title");
const artistNameField = document.getElementById("artistNameField");
const artistBirthdateField = document.getElementById("artistBirthDateField");
const artistListenersField = document.getElementById("artistListenersField");

const songsButton = document.getElementById("songsButton");
const songsTable = document.getElementById("songsTable");
const songsTableBody = document.getElementById("songsTableBody");

const url = window.location.href
const artistId = url.match("(/artists/)([0-9]*)")[2];

function redirectToArtistEditor() {
    window.location.replace(`/artists/${artistId}/editor`)
}

async function deleteArtist() {
    const response = await fetch("/api/artists/" + artistId, {
        method:"DELETE"
    })
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

function enableSongsTable() {
    // TODO
    // songsTable.enabled = true;
    // songsTable.classes.replace("disabled", "");
    // songsTable.class;
}

async function fetchSongs() {
    return fetch(`/api/artists/${artistId}/songs`).then(response => response.json());
}

async function fillSongsTable() {
    const songs = await fetchSongs();

    songsTableBody.innerHTML = "";
    for (let i = 0; i < songs.length; i++) {
        const song = songs[i];
        songsTableBody.innerHTML += `
        <tr class="table100-head">
            <td class="px-3"><p class="text-black-50 text-center">${song.name}</p></td>
            <td class="px-3"><a href="/songs/${song.id}" class="text-black-50">Details</a></td>
        </tr>
        `;
    }
}

async function showSongsTable() {
    enableSongsTable();
    await fillSongsTable();
}

function addEventListeners() {
    editButton.addEventListener("click", redirectToArtistEditor);
    deleteButton.addEventListener("click", deleteArtist);
    songsButton.addEventListener("click", showSongsTable)
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

