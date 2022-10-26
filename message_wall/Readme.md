对象 和 JSON 字符串之间的转换

Java
objectMapper.readValue 把 JSON 字符串转换成 对象，写在 doPost() 方法里
objectMapper.writeValueAsString 把对象 转换成 JSON 字符串，写在 doGet() 方法里

JS
- JSON.parse 把 JSON 字符串 转换成 对象
  写在function getMessages() {}

- JSON.stringfy 把 对象 转换成 JSON 字符串
  写在 submit.onclick = function(){} 里面
