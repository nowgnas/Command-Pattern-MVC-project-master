$(document).on("click", "#registerBtn", register);

// 아이디 중복 확인 추가하기

async function register() {
    console.log("register");
    let registName = $("#registName").val();
    let registId = $("#registId").val();
    let registPassword = $("#registPassword").val();
    console.log(registId);
    // 비밀번화 확인하기

    let data = JSON.stringify({sign: "register", registId, registName, registPassword});
    data = await fetch("main", {method: "post", body: data});
    data = await data.text();
    console.log(data.state);
}