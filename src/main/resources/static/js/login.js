$('form').submit(function(e) {
    e.preventDefault();
    const nickname = $("#nickname").val();
    const password = $("#password").val();

    const dto = {
        nickname,
        password
    }

    $.ajax({
        url: "/api/v1/admin/auth/login",
        data: dto,
        method: "POST",
        dataType: "json"
    })
    .done(function(json) {
        window.localStorage.setItem("token", json.token);
        location.href="/admin";
    })
    .fail(function(xhr, status, error) {
        if (xhr.status === 401) {
            alert('로그인 실패');
        } else if (xhr.status === 500) {
            alert('서버 에러');
        }
    })
});
