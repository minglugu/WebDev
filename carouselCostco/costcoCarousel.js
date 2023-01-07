const imgArr = [
    'https://mobilecontent.costco.com/live/resource/img/ca-homepage/d-hero-1019-22-P6-MVM-Wk1-en.jpg',
    'https://mobilecontent.costco.com/live/resource/img/ca-homepage/d-hero-220131-Limited-en1.jpg',
    'https://mobilecontent.costco.com/live/resource/img/ca-homepage/d-hero-220131-apple-watch-en.jpg',
    'https://mobilecontent.costco.com/live/resource/img/ca-homepage/d-hero-220131-ghostbed-en.jpg',
    'https://mobilecontent.costco.com/live/resource/img/ca-homepage/d-hero-220131-pirelli-en.jpg',
    'https://mobilecontent.costco.com/live/resource/img/ca-homepage/d-hero-220124-tvs-football-en.jpg'
]
const btnArr = [
    'Member-only Savings',
    'Limited-time Offers',
    'Apple Watch',
    'Ghostbed',
    'Pirelli',
    'Televisions'
]

// 1. creat image and button array variables
let imgIndex = 0

let eleImgArr = [] // create current image variable
let eleBtnArr = [] // create current button variable

// 2. find the nodes
const clickIndex = 'LuIndex'
const imgContainer = document.getElementById('imgContainer')
const btnContainer = document.getElementById('btnContainer')

// 3. create click function and add this function to button (此函数在button元素中添加了index属性，点击相应index位置的button,将会激活相应位置的图片）
function clickFun(evt) {
    imgIndex = evt.target.getAttribute(clickIndex)
    switchImgBtn()
    clearInterval(timerID2)
}

// 4. create elements dynamically based on the array (通过js在div框中添加image和button元素, 也可在html中直接添加）
function createCarousel(){
    // var i
    for (let i = 0; i < imgArr.length; i++){
        //create img
        let eleImg = document.createElement('img')
        eleImg.src = imgArr[i]
        eleImg.width = 1400
        imgContainer.appendChild(eleImg)
        //save the img node to an array
        eleImgArr.push(eleImg)

        //create btns
        let eleBtn = document.createElement('button')
        eleBtn.innerHTML = btnArr[i]
        eleBtn.setAttribute(clickIndex, i)
        //add eventListener
        eleBtn.addEventListener('click', clickFun)
        btnContainer.appendChild(eleBtn)
        eleBtnArr.push(eleBtn)

        // Add forward/backward buttons to show next/previous image
        let prevAnchor = document.createElement('a')
        let nextAnchor = document.createElement('a')
        prevAnchor.className = 'prev'
        // prevAnchor.src = <i class="fa-solid fa-chevron-left"></i> //从fontawesome里加i标签
        nextAnchor.className = 'next'
        // nextAnchor.innerHTML= //从fontawesome里加i标签
    }
}

// 5. Creating a function can change images and buttons (创建能自动播放的图片和按钮功能）
function switchImgBtn (){
    //hide all img element
    for (let i = 0; i < eleImgArr.length; i++) {
        eleImgArr[i].style.display = 'none'
        eleBtnArr[i].className = eleBtnArr[i].className.replace('active', '')
    }
    imgIndex++
    if (imgIndex > eleImgArr.length) {imgIndex = 1}
    eleImgArr[imgIndex - 1].style.display = 'block'
    eleBtnArr[imgIndex - 1].className += 'active'
    }


// 6. Creating a function can choose image by previous and next anchor

createCarousel()
switchImgBtn()
timerID2 = setInterval(switchImgBtn, 2000);


// 7. the following is the newly adeed code for clickable arrows on the carousel

/*use data attribute (e.g. [data-carousel-arrow]) here instead of class name because it makes working with javascript
much easier because I don't have to worry about overlapping between classes and javascript.
so on the button attribute, I add "data-carousel-arrow" in costcoCarousel.html
*/
/* Q: What are data attributes？
   A: They start with data-attribute-name 
   reference URL: https://developer.mozilla.org/en-US/docs/Learn/HTML/Howto/Use_data_attributes
*/ 

    // select all the arrows on the images (left and right arrow)
   const arrows = document.querySelectorAll("[data-carousel-arrow]")

   // loop through each of the carousel images: this function swithcImgBtn() does the looping work
   // for each of the arrow, addEventListener which will be the "click" event listener.
   // Then it just moves to the next image [this code: () => {}] by calling switchImgBtn() function.
   arrows.forEach(arrow => {
       arrow.addEventListener("click", () => {
           switchImgBtn()
       })
   })
   


