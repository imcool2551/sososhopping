$('.nav-main').click(function() {
    redirect("/admin/main")
      .catch(alert);
})

$('.menu-store-register').click(function() {
    redirect("/admin/storeRegister")
      .catch(alert);
})

$('.menu-user-report').click(function() {
    redirect("/admin/userReport")
      .catch(alert);
})

$('.menu-store-report').click(function() {
    redirect("/admin/storeReport")
      .catch(alert);
})

$('.btn-logout').click(function () {
    window.localStorage.removeItem("token");
    window.location.href = "/admin/login";
})