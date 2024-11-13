$().ready(function () {
  $(".search-btn").on("click", function () {
    movePage(0);
  });

  $(".search-type").on("change", function () {
    var type = $(this).val();

    var keywordDom = $(".search-keyword");

    if (type === "boardId") {
      keywordDom.attr("type", "number");
    } else if (type === "email") {
      keywordDom.attr("type", "email");
    } else {
      keywordDom.attr("type", "text");
    }
  });

  $(".search-type").change();

  $(".list-size").on("change", function () {
    // var listSize = $(this).val();
    // location.href = "/board/list?pageNo=0&listSize=" + listSize;

    movePage(0);

    // $(".page-no").val("0");
    // $(".search-form")
    //   .attr({
    //     method: "GET",
    //     action: "/board/list",
    //   })
    //   .submit();
  });
});

function movePage(pageNo) {
  $(".page-no").val(pageNo);
  $(".search-form")
    .attr({
      method: "GET",
      action: "/board/list",
    })
    .submit();
}
