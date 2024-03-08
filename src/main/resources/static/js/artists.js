const artistRecordsTable = document.getElementById("artistRecordsList");

async function fetchArtistsJson() {
    const artistsResponse = await fetch("api/artists");
    return await artistsResponse.json();
}

async function fillInArtistsRecordsTable() {
    const artists = await fetchArtistsJson();
    console.log(artists)
    artistRecordsTable.innerHTML = "";
    // for (const artist in artists) {
    for (let i = 0; i < artists.length; i++) {
        const artist = artists[i]
        console.log(artist)
        artistRecordsTable.innerHTML += `
        <tr class="row d-table-row">
            <td class="col-auto px-3 py-2 d-flex justify-content-center">
                <a href="/artists/${artist.id}" class="text-black-50">${artist.name}</a>
            </td>
        </tr>
        `;
    }
}

fillInArtistsRecordsTable();