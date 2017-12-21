/**
 * 
 */
/* ---------------------------------------------------------------------------------
                                商品詳細からの値受取
 --------------------------------------------------------------------------------- */
$(function(){
    $('#editItem').click(function(){
        var id = $('#itemId').text();
        console.log('編集id;'+id)
        $.ajax({
            type: "GET",
            url: "http://localhost:8080/ajax/editItem?id=" + id,
            data: "text",
            dataType: "text"
        }).then(function(){
            console.log('成功')
        })
    })

});
