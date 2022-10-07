window.onload = function () {
    let loginId = $.cookie("loginId");

    if (loginId) {
        login(loginId);
    }
}

$(document).on("click", "#loginBtn", login);
$(document).on("click", "#logoutBtn", logout);
$(document).on("change", "#sido", getGugun);
$(document).on("change", "#gugun", getDong);
$(document).on("change", "#dong", selectDong);
$(document).on("change", "#year", selectYear);
$(document).on("change", "#month", selectMonth)
$(document).on("click", "#houseDealInfoBtn", getHouseDealInfo);


let month;

function selectMonth() {
    month = $("#month option:selected").val();
}

let year;

function selectYear() {
    year = $("#year option:selected").val();
}

let dong;

function selectDong() {
    dong = $("#dong option:selected").val();
}


async function getHouseDealInfo() {
    console.log(sido, gugun, dong, year, month);
    if (sido == undefined || gugun == undefined || dong == undefined || year == undefined || month == undefined) {
        alert("검색 조건을 모두 선택해 주세요");
    } else {
        let data = JSON.stringify({sign: "getHouseDealInfo", sido, gugun, dong, year, month});
        data = await fetch("main", {method: "POST", body: data});
        data = await data.text();
        console.log(data);
        data = JSON.parse(data);
        console.log(data);
        let houseDealInfoListTable = `  <table class="table table-hover">
								    <thead>
								      <tr>
								        <th>no</th>
								        <th>dong</th>
								        <th>roadName</th>
								        <th>apartmentName</th>
								        <th>floor</th>
								        <th>area</th>
								        <th>dealAmount</th>
								      </tr>
								    </thead><tbody>`;
        data.houseDealInfoList.forEach(function (item, index) {
            console.log(item);
            item = JSON.parse(item);
            houseDealInfoListTable += `<tr onclick="alert(${item.lat}+':'+${item.lng})">
								        <th>${item.no}</th>
								        <th>${item.dong}</th>
								        <th>${item.roadName}</th>
								        <th>${item.apartmentName}</th>
								        <th>${item.floor}</th>
								        <th>${item.area}</th>
								        <th>${item.dealAmount}</th>
								      </tr>`;
        });

        houseDealInfoListTable += `</tbody></table>`;

        $("#contentTopDiv").html(houseDealInfoListTable);

    }
}


let gugun;

async function getDong() {
    sido = $("#sido option:selected").val();
    gugun = $("#gugun option:selected").val();

    if (sido.length > 2 && gugun.length > 1) {
        let data = JSON.stringify({sign: "getDong", sido, gugun});
        data = await fetch("main", {method: "POST", body: data});
        data = await data.text();
        data = JSON.parse(data);
        let options = `<option value="">동</option>`;
        data.dong.forEach(function (item, index) {
            options += `<option value="${item}">${item}</option>`;
        });

        $("#dong").html(options);
    }

}

let sido;

async function getGugun() {
    sido = $("#sido option:selected").val();

    if (sido.length > 2) {
        let data = JSON.stringify({sign: "getGugun", sido});
        data = await fetch("main", {method: "POST", body: data});
        data = await data.text();
        data = JSON.parse(data);
        console.log(data);
        let options = `<option value="">구군</option>`;
        data.gugunList.forEach(function (item, index) {
            options += `<option value="${item}">${item}</option>`;
        });

        $("#gugun").html(options);

    }
}


async function logout() {
    let data = JSON.stringify({sign: "logout"});
    await fetch("main", {method: "post", body: data});
    $.removeCookie("loginId");
    location.reload();

}

async function login(id) {
    console.log(id);
    let data;
    if (typeof id == "string") { // 로그인 되어있는 경우
        data = JSON.stringify({sign: "login", id});
    } else { // 로그인 하는 경우
        let id = $("#id").val();
        let pw = $("#pw").val();
        data = JSON.stringify({sign: "login", id, pw});
    }

    data = await fetch("main", {method: "POST", body: data});
    console.log(data);
    data = await data.text();
    console.log(data);
    data = JSON.parse(data);
    console.log(data);

    if (data.loginId) {
        $.cookie("loginId", data.loginId);
        $("#loginDiv").html(`<div class="text-white">${data.loginId} <button class="btn btn-primary" id="logoutBtn">logout</button></div>`);
    } else {
        alert(data.msg);
        $.removeCookie("loginId");
    }
}