$('.btn-submit').on('click', function(e) {
    e.preventDefault();

    const formData = $(this).closest('form').serializeArray();
    formData.push({ name: this.name, value: this.value });

    $.ajax({
        method: "POST",
        url: "/admin/storeRegister",
        data: formData,
        beforeSend: function(xhr) {
            xhr.setRequestHeader("token", window.localStorage.getItem("token"));
        }
    })
    .done(function() {
        redirect("/admin/storeRegister");
    })
    .fail(function(xhr) {
        if (xhr.status === 400) {
          alert('알 수 없는 요청입니다')
        } else if (xhr.status === 404) {
          alert('존재하지 않는 점포 혹은 신고입니다');
        } else if (xhr.status === 500) {
          alert('서버 오류');
        }
    })
})