
function displayFiles(data){
    var items = document.getElementById('items');
    items.innerHTML = '';
    for (var i in data){
        var item = '<div class="panel panel-info">\n' +
            '                                        <div class="panel-heading">\n' +
            '                                            <h4 class="panel-title">\n' +
            '                                                <a id="title'+i+'" data-toggle="collapse" href="#detail'+i+'">\n' +
                                                                data[i].name +
            '                                                </a>\n' +
            '                                            </h4>\n' +
            '                                        </div>\n' +
            '                                        <div id="detail'+i+'" class="panel-collapse collapse">\n' +
            '                                            <div class="panel-body">\n<p style="font-size: 14px">' +
            '                                                <b>Author:</b> '+data[i].author + '<br>' +
            '                                                <b>Type:</b> <span class="label label-primary">'+data[i].type+'</span>&nbsp;&nbsp;' +
            '                                                <b>Download:</b> <a href="'+data[i].url+'"><span class="label label-info">download</span></a><br>' +
            '                                                <b>Desc:</b> '+data[i].description+'</p>' +
            '                                            </div>\n' +
            '                                        </div>\n' +
            '                                    </div>';
        items.innerHTML += item;
    }
}

function getFiles(){
    $.ajax({
        url: 'getSARFiles',
        type: 'get',
        success: function (data) {
            displayFiles(data)
        }
    });
}

getFiles();


function search() {
    console.log('start search...')
    var searchContent = document.getElementById("searchContent");
    searchContent = searchContent.value;
    if (searchContent == null || searchContent == "") {
        alert("关键词不能为空！");
    }else {
        $.ajax({
            url: 'getSARFilesWithFilter',
            type: 'post',
            data: {
                content: searchContent
            },
            success: function (data) {
                displayFiles(data)
            }
        })
    }
}

function newItem() {
    console.log('new item');
}

function clearKeyWords() {
    console.log('clear button');
    document.getElementById("searchContent").value = '';
    getFiles();
}

var uid;
$(function () {
    $("#btn1").on("click", function () {
        console.log($("#f_upload")[0].files.length);
        console.log($("#f_upload")[0].files);
        console.log($("#f_upload")[0]);
        var files = $("#f_upload")[0].files;
        // var formData = new FormData();
        // for (var i = 0; i < files.length; i++){
        //     var file = files[i];
        //     formData.append("file", file);
        // }
        console.log(formData);
        console.log(formData.get("file"));
        $.ajax({
            type: "POST",              //因为是传输文件，所以必须是post
            url: 'uploadFiles',         //对应的后台处理类的地址
            data: {
                files: files,

            },
            processData: false,
            contentType: false,
            success: function (data) {
                console.log(data);
                window.location.reload(true);
            },
            error: function (data) {
                console.log("fail");
            }
        });
    });

    $("#btn2").on("click", function () {
        $("#f_upload").fileinput('clear').fileinput('unlock');
    });

    $("#f_upload").fileinput({
        language:'zh',//设置文中文
        uploadUrl:'uploadFiles',//图片上传的url，我这里对应的是后台struts配置好的的action方法
        showCaption:true,//显示标题
        showRemove:true, //显示移除按钮
        uploadAsync:true,//默认异步上传
        showUpload:false,//显示上传按钮
        showPreview:true,//是否显示预览
        textEncoding:"UTF-8",//文本编码
        autoReplaceBoolean:false,//选择图片时不清空原图片
    });

    $("#f_upload").on('fileuploaded', function(event, data, previewId, index) {//异步上传成功结果处理
        $(event.target)
            .fileinput('clear')
            .fileinput('unlock');
        var result = data.response;
        console.log(result);
    });

});
