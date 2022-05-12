function adminLoginRequest() {
     const nickname = $("#nickname").val();
     const password = $("#password").val();
     const dto = {
         nickname,
         password
     };

     return new Promise((resolve, reject) => {
         $.ajax({
             method: "POST",
             url: "/api/v1/admin/auth/login",
             data: dto,
             dataType: "json"
         })
         .done(function(json) {
             window.localStorage.setItem("token", json.token);
             resolve();
         })
         .fail(function(xhr) {
              if (xhr.status === 401) {
                  reject('로그인 실패');
              } else if (xhr.status === 500) {
                  reject('서버 에러');
              }
          })
     })
}

$('.form-group').submit(function(e) {
    e.preventDefault();

    adminLoginRequest()
    .then(() => {
        return redirect("/admin/main");
    })
    .catch(alert)
});
