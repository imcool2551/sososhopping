$('.btn-logout').click(function () {
    window.localStorage.removeItem("token");
    location.href="/admin/login";
})