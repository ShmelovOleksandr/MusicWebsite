// import '../scss/search-issues.scss'
import 'bootstrap'

import {header, token} from './util/csrf.js'
import axios from "axios";
import anime from "animejs";

const editButton= document.getElementById('editButton')
const deleteButton = document.getElementById('deleteButton')
const title = document.getElementById('title')
const artistNameField = document.getElementById('artistNameField')
const artistBirthdateField = document.getElementById('artistBirthDateField')
const artistListenersField = document.getElementById('artistListenersField')

const songsButton = document.getElementById('songsButton')
const songsTable = document.getElementById('songsTable')
const songsTableBody = document.getElementById('songsTableBody')

const toursButton = document.getElementById('toursButton')
const toursTable = document.getElementById('toursTable')
const toursTableBody = document.getElementById('toursTableBody')

const url = window.location.href
const artistId = url.match('(/artists/)([0-9]*)')[2]

let songsTableIsEnabled = false
let toursTableIsEnabled = false

function redirectToArtistEditor() {
    window.location.replace(`/artists/${artistId}/editor`)
}

async function deleteArtist() {
    try {
        const response = await axios.delete(`/api/artists/${artistId}`, {
            headers: {
                [header]: token
            }
        });
        if (response.status === 204) {
            window.location.replace('/artists');
        }
    } catch (error) {
        console.error("An error occurred while deleting the artist:", error);
    }
}

function updateTextFields(artist) {
    // window.location.head.title = artist.name
    title.innerText = artist.name
}

function updateArtistDetails(artist) {
    artistNameField.innerText = artist.name
    artistBirthdateField.innerText = artist.birthDate
    artistListenersField.innerText = artist.listeners
}

async function toggleSongsTable() {
    if (songsTableIsEnabled)
        hideSongsTable()
    else
        await showSongsTable()
}

async function showSongsTable() {
    enableSongsTable()
    await fillSongsTable()
    songsButton.innerText = 'Hide songs'
}

function hideSongsTable() {
    disableSongsTable()
    songsButton.innerText = 'Show songs'
}

function enableSongsTable() {
    songsTable.classList.remove('d-none')
    anime({
        targets: songsTable,
        opacity: 1,     // Target opacity value
        duration: 1000, // Animation duration in milliseconds
        easing: 'easeInOutQuad' // Easing function
    });
    songsTableIsEnabled = true
}

function disableSongsTable() {
    anime({
        targets: songsTable,
        opacity: 0,     // Target opacity value
        duration: 1000, // Animation duration in milliseconds
        easing: 'easeInOutQuad', // Easing function
        complete: function() {
            // Add 'd-none' class after the animation completes
            songsTable.classList.add('d-none');
        }
    });
    songsTableIsEnabled = false
}

async function fetchSongs() {
    try {
        const response = await axios.get(`/api/artists/${artistId}/songs`);
        return response.data;
    } catch (error) {
        console.error("An error occurred while fetching the songs:", error);
    }
}

async function fillSongsTable() {
    const songs = await fetchSongs()

    songsTableBody.innerHTML = ''
    for (let i = 0; i < songs.length; i++) {
        const song = songs[i]
        songsTableBody.innerHTML += `
        <tr class="table100-head">
            <td class="px-3"><p class="text-black-50 text-center">${song.name}</p></td>
            <td class="px-3"><a href="/songs/${song.id}" class="text-black-50">Details</a></td>
        </tr>
        `
    }
}

async function toggleToursTable() {
    if (toursTableIsEnabled)
        hideToursTable()
    else
        await showToursTable()
}

async function showToursTable() {
    enableToursTable()
    await fillToursTable()
    toursButton.innerText = 'Hide tours'
}

function hideToursTable() {
    disableToursTable()
    toursButton.innerText = 'Show tours'
}

function enableToursTable() {
    toursTable.classList.remove('d-none')
    anime({
        targets: toursTable,
        opacity: 1,     // Target opacity value
        duration: 1000, // Animation duration in milliseconds
        easing: 'easeInOutQuad' // Easing function
    });
    toursTableIsEnabled = true
}

function disableToursTable() {
    anime({
        targets: toursTable,
        opacity: 0,     // Target opacity value
        duration: 1000, // Animation duration in milliseconds
        easing: 'easeInOutQuad', // Easing function
        complete: function() {
            // Add 'd-none' class after the animation completes
            toursTable.classList.add('d-none');
        }
    });
    toursTableIsEnabled = false
}

async function fetchTours() {
    try {
        const response = await axios.get(`/api/artists/${artistId}/tours`);
        return response.data;
    } catch (error) {
        console.error("An error occurred while fetching the tours:", error);
    }
}

async function fillToursTable() {
    const tours = await fetchTours()

    toursTableBody.innerHTML = ''
    for (let i = 0; i < tours.length; i++) {
        const tour = tours[i]
        toursTableBody.innerHTML += `
        <tr class="table100-head">
            <td class="px-3"><span class="text-black-50">${tour.location}</span></td>
            <td class="px-3"><a href="/tours/${tour.id}" class="text-black-50">Details</a></td>
        </tr>
        `
    }
}

function addEventListeners() {
    editButton?.addEventListener('click', redirectToArtistEditor)
    deleteButton?.addEventListener('click', deleteArtist)
    songsButton?.addEventListener('click', toggleSongsTable)
    toursButton?.addEventListener('click', toggleToursTable)
}

async function updateArtistDetailsPage() {
    // const artist = await fetchArtistJson();
    const artistJson = await fetch('/api/artists/' + artistId)
    const artist = await artistJson.json()
    updateArtistDetails(artist)
    updateTextFields(artist)
}

addEventListeners()
updateArtistDetailsPage()

