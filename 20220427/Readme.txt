1.  HTML 标签里的属性，可以通过JS中的DOM对象来获取到一样的属性 
    对象名.属性
    e.g. input.value

2.  DOM 对象一共有多少个属性
    用 console.dir 这个方法，打印出一个 dom 对象的全部属性和值

3.  获取表单的元素，这些元素，属于表单元素专有的属性
    value: input 的值
    disabled: 禁用
    checked: 复选框使用
    selected: 下拉框使用
    type: input 的类型（文本，密码，按钮，文件等）

4.  在 HTML 中，表示类名的属性，就是class 但是在 JS 里，属性名变成了 className/classList
    class 在 JS 中，也是一个关键字
    JS ES6 版本以上，也引入了 class，这个概念

5.  