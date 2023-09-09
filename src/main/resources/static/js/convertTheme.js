// checking whether current theme is dark or not
if (localStorage.getItem('theme') === 'dark') {
    toDarkTheme();
}

// converting theme
document.getElementById('theme').addEventListener('change', convertTheme);

function convertTheme() {
    let theme_1 = document.getElementById('theme');
    let theme_2 = theme_1.options[theme_1.selectedIndex].value;

    if (theme_2 === 'dark') {

        toDarkTheme();
        // local storage
        localStorage.setItem('theme', 'dark');

    } else {

        toLightTheme();
        // local storage
        localStorage.setItem('theme', 'light');
    }
}

function toDarkTheme() {
    // navbar and sidebar background
    let bg_light = document.querySelectorAll('.bg-light');

    for (let i = 0; i < bg_light.length; i++) {
        bg_light[i].classList.replace('bg-light', 'bg-secondary');
    }

    // navbar and sidebar color
    let link_dark = document.querySelectorAll('a');

    for (let i = 0; i < link_dark.length; i++) {
        if (link_dark[i].classList.contains('dropdown-item')) {
            continue;
        }

        link_dark[i].classList.remove('link-dark');
        link_dark[i].classList.add('link-light');
    }

    // main
    let body = document.querySelector('body');
    body.classList.add('bg-dark');
    body.classList.add('text-light');

    // table font color
    let table_font_color = document.querySelectorAll('.table');

    for(let i=0; i<table_font_color.length; i++) {
        table_font_color[i].classList.add('text-light');
    }
}

function toLightTheme() {
    // navbar and sidebar
    let bg_secondary = document.querySelectorAll('.bg-secondary');

    for (let i = 0; i < bg_secondary.length; i++) {
        bg_secondary[i].classList.replace('bg-secondary', 'bg-light');
    }

    // navbar and sidebar color
    let link_light = document.querySelectorAll('a');

    for (let i = 0; i < link_light.length; i++) {
        if (link_light[i].classList.contains('dropdown-item')) {
            continue;
        }

        link_light[i].classList.remove('link-light');
        link_light[i].classList.add('link-dark');
    }

    // main
    let body = document.querySelector('body');
    body.classList.remove('bg-dark');
    body.classList.remove('text-light');

     // table font color
     let table_font_color = document.querySelectorAll('.table');

     for(let i=0; i<table_font_color.length; i++) {
         table_font_color[i].classList.remove('text-light');
     }
}