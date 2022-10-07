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
$(document).on("change", "#dong", getHouseDealInfo);

async function getHouseDealInfo() {
    let sido = $("#sido option:selected").val();
    let gugun = $("#gugun option:selected").val();
    let dong = $("#dong option:selected").val();


}

async function getDong() {
    let sido = $("#sido option:selected").val();
    let gugun = $("#gugun option:selected").val();

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


async function getGugun() {
    let sido = $("#sido option:selected").val();

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