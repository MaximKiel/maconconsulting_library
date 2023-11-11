function showList(){
    fetch("http://localhost:8080/users")
        .then(response => response.json())
        .then(data => createTable(data));
}
function createTable(data) {
    let table = "<table>";
    table += `<tr>
                  <th>Name</th>
                  <th>Email</th>
                  <th>Role</th>
                </tr>`;
    // let tr = "";
    // for(let i = 0; i < data.maconUsers.length; i++) {
    //     tr += "<tr>";
    //     tr += `<td>${data.maconUsers[i].name}</td>`;
    //     tr += `<td>${data.maconUsers[i].email}</td>`;
    //     tr += `<td>${data.maconUsers[i].role}</td>`;
    //     tr += "</tr>"
    // }
    // table += tr + "</table>";
    document.body.innerHTML += table;
}