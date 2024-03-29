/* ========= MENU SHOW Y HIDDEN ========= */
const navMenu = document.getElementById('nav-menu'),
      navToggle = document.getElementById('nav-toggle'),
      navClose = document.getElementById('nav-close')

/* ========= MENU SHOW ========= */
// Validate if constant exists
if(navToggle) {
    navToggle.addEventListener('click', () => {
        navMenu.classList.add('show-menu')
    })
}

/* ========= MENU HIDDEN ========= */
// Validate if constant exists
if(navClose) {
    navClose.addEventListener('click', () => {
        navMenu.classList.remove('show-menu')
    })
}


/* ========= REMOVE MENU MOBILE ========= */
const navLink = document.querySelectorAll('.nav__link')

function linkAction(){
    const navMenu = document.getElementById('nav-menu')
    // When we click on each nav__link, we remove the show-menu class
    navMenu.classList.remove('show-menu')
}
// click any of the menu icon, the whole menu will be removed
navLink.forEach(i => i.addEventListener('click', linkAction))


/* ========= ACCORDION SKILLS ========= */
// see the class of skills__content and skills__header in index.html file 
const skillsContent = document.getElementsByClassName('skills__content'),
      skillsHeader = document.querySelectorAll('.skill__header')  

function toggleSkills() {
    let itemClass = this.parentNode.className

    for (i = 0; i < skillsContent.length; i++) {
        skillsContent[i].className = 'skills__content skills__close'
    }
    if(itemClass === 'skills__content skills__close') {
        this.parentNode.className = 'skills__content skills__open'
    }
}

// for each skills header element(el)
skillsHeader.forEach((el) => {
    el.addEventListener('click', toggleSkills)
})
/* ========= QUALIFICATION TABS ========= */


/* ========= MODAL ========= */


