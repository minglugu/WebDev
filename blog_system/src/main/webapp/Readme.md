DBUtil: video JAVA EE 初阶 #35 2022-05-23 video


# 在blog edit页面，引入 editor.md 编辑器

# 页面.html
<div id="editor"> </div>

# 初始化.js
<!-- 初始化编辑器 -->
var editor = editormd("editor", {
    <!-- 尺寸必须在这里设置，设置样式会被 editormd 自动覆盖掉 -->
    width: "100%",
    <!-- 设定编辑器高度 -->
    height: "500px",
    <!-- 编辑器中的初始化内容 -->
    markdown: "# 在这里写下一篇博客",
    <!-- 指定 editor.md 依赖的插件路径 -->
    path: "editor.md/lib/"
});

# 获得编辑框的内容.js
<!-- 获取 markdown 格式的数据 -->
editor.getMarkdown();
<!-- 获取 html 格式的数据 -->
editor.getHTML();


1. download editor.md: https://pandao.github.io/editor.md/en.html
2. save the editor.md folder into the project

3. import editor.md in blog_edit.html

4. jQuery 不是 editor.md 的一部分,而是 editor.md 的依赖库
   所以还要引入 jQuery. 
   - releases.jquery.com
   - jQuery Core 3.6.0 minified version
   - copy the path in src(https://code.jquery.com/jquery-3.6.1.min.js), paste in Google 
   - 打开 jQuery 的源码文件,ctrl+A, and ctrl+v in jquery.min.js 里面. 或者直接复制<script>
    src="https://code.jquery.com/jquery-3.6.1.min.js"
    integrity="sha256-o88AwQnZB+VDvE9tvIXrMQaPlFFSUTR+nldQm1LuPXQ="
    crossorigin="anonymous"></script>, 但有时不稳定.建议复制到本地.

## 前端引入第三方库，其实是非常容易的。
## 从官网，下载好第三方库，保存到自己的项目目录中，在html中引入 css或js 文件。