// import '../scss/search-issues.scss'
import 'bootstrap'
import axios from "axios";

const artistRecordsTable = document.getElementById('artistRecordsList')

async function fetchArtistsJson() {
    try {
        const response = await axios.get('/api/artists');
        return response.data;
    } catch (error) {
        console.error("An error occurred while fetching artists:", error);
    }
}

async function fillInArtistsRecordsTable() {
    const artists = await fetchArtistsJson()
    artistRecordsTable.innerHTML = ''
    for (let i = 0; i < artists.length; i++) {
        const artist = artists[i]
        artistRecordsTable.innerHTML += `
        <tr class="row d-table-row">
            <td class="col-auto px-3 py-2 d-flex justify-content-center">
                <a href="/artists/${artist.id}" class="text-black-50">${artist.name}</a>
            </td>
        </tr>
        `
    }
}

fillInArtistsRecordsTable()