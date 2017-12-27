/**
 * 
 */


/* -------------------------------------------------------------------------------
                                category
---------------------------------------------------------------------------------*/
$(function () {
    $.ajax({
        url: "http://localhost:8080/ajax/autocomplete",
        dataType: "json",
        type: 'GET'
    }).then(function (categoryName) {
        console.log(categoryName.length)
        categoryName.forEach(name => {
            console.log('カテゴリーid:'+name.id)
            $('#categoryOfParent').append("<option value=" + name.id + ">" + name.name + "</option>")
        })
    })
})
$(function () {
    $('#categoryOfParent').change(function () {
        $('#categoryOfChild').html("<option value='null' style ='color:red'>【child category】please choise!!</option>")
        var nameOfCategory = $('#categoryOfParent').val();
        console.log('parantのid:'+nameOfCategory)
        $.ajax({
            type: "GET",
            url: "http://localhost:8080/ajax/autocompleteOfChild?id=" + nameOfCategory,
            dataType: "json" //配列を受取る場合はjson型を指定する。textだと配列が一つの文字列として認識されてしまう。
        }).then(function (categoryName) {
            console.log(categoryName.length)
            $.each(categoryName, function (index, val) {
                console.log('childName:'+val.name)
                console.log('childId:'+val.id)
                $('#categoryOfChild').append("<option value=" + val.id + " class='childOption'>" + val.name + "</option>")
            })
        })
    })
})
$(function () {
    $('#categoryOfChild').change(function () {
        $('#categoryOfGrandChild').html("<option value='null' style ='color:red'>【grandchild category】please choise!!</option>")
        var nameOfCategory = $('#categoryOfChild').val();
        console.log('childのID：'+ nameOfCategory)
        $.ajax({
            type: "GET",
            url: "http://localhost:8080/ajax/autocompleteOfChild?id=" + nameOfCategory,
            dataType: "text"//もしtextで配列を受取りたい場合は後でjson.parse()をする必要がある
        }).then(function (categoryName) {
            //下記も比較的新しいループのやり方
            for (let name of JSON.parse(categoryName)) {
                console.log("孫のid"+name.id+":::孫の名前"+name.name)
                $('#categoryOfGrandChild').append("<option value=" + name.id + " class='grandChildOption'>" + name.name + "</option>")
            }
        })
    })
});
/* ---------------------------------------------------------------------------------------
                                        brand
----------------------------------------------------------------------------------------- */
$(function () {
/*     $('#brand').select(function () {
        $('#brand').remove
        var nameOfBrand = $('#brand').val()
        console.log(nameOfBrand) */
        $.ajax({
            url: "http://localhost:8080/ajax/autocompleteForBrand" /* + nameOfBrand */,
            dataType: "json",
            type: 'GET'
        }).then(function (brandArray) {
            console.log(brandArray.length)
            $('#brand').autocomplete({
                source: brandArray,
                autoFocus: true,
                delay: 500,
                minLength: 2,
                collapsible: true
            });
        })
    })
/* -------------------------------------------------------------------------------------
                                    condition
---------------------------------------------------------------------------------------*/
$(function(){
    var condition = $('#hiddenCondition').val();
    console.log(condition)
    $('.inputCondition').val()=condition;
});