<#include "../../layout/layout.ftl"/>
<@Head title="图片上传显示">
</@Head>
<@Body>
<blockquote class="layui-elem-quote">
    <button type="button" class="layui-btn" id="uploadImage">
        <i class="layui-icon">&#xe62f;</i>上传图片
    </button>
    <button type="button" class="layui-btn" id="showImage">
        <i class="layui-icon">&#xe601;</i>显示图片
    </button>
</blockquote>
<div>
    <!-- 图片位置动态显示你要显示的图片     这里是写死的-->
    <img src="" id="testImage" style="width: 200px"/>
</div>

<script>
    layui.use(['element','upload'], function() {
        var element = layui.element;
        var upload = layui.upload;


        upload.render({
            elem: '#uploadImage',
            url: 'admin/image/upload',
            accept: 'file', //普通文件
            done: function (res) {
                //上传完毕
                if (res.code == 0) {
                    layer.alert("上传成功", {icon: 6}, function (index) {
                        layer.close(index);
                        $("#testImage").attr("src","admin/image/show?fileName=" + res.data);
                    })
                } else {
                    layer.msg('上传失败');
                }

            }
        });

        $('#showImage').on('click', function () {

            $("#testImage").attr("src","admin/image/show?fileName=1c33c7b067ae4a9aa4d2260585ef4ada.jpg");

        })
    })


</script>

</@Body>