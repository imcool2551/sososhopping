/*
 * LocalStorage 의 토큰을 헤더에 넣어서 요청하고
 * 받은 html로 DOM 재생성 + 주소창 업데이트
 */
function redirect(url) {
    return new Promise((resolve, reject) => {
        $.ajax({
            method: "GET",
            url,
            beforeSend: function(xhr) {
                xhr.setRequestHeader("token", window.localStorage.getItem("token"));
            },
            dataType: "html"
        })
        .done(function(html) {
            // re-writes the entire document with title
            var newDoc = document.open("text/html", "replace");
            newDoc.write(html);
            newDoc.close();
            // changes url
            window.history.pushState("", "", url);
            resolve();
        })
        .fail(function(xhr) {
            if (xhr.status === 403) {
                reject("접근 권한이 없습니다");
            } else if (xhr.status === 404) {
                reject("없는 페이지 입니다");
            } else if (xhr.status === 500) {
                reject('서버 에러');
            }
        })
    })
}