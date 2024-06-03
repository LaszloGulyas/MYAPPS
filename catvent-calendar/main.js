'use strict';

function revealContent(elementId) {
    const element = document.getElementById(elementId);
    if (validateRevealRequest(elementId)) {
        element.onclick = null;
        fadeOut(elementId);
        updateCookie(elementId, 1);
    } else {
        alert('Ki ma türelmetlen, az unatkozna a jövőben!');
    }
}

function validateRevealRequest(elementId) {
    const currentDate = new Date();
    const requestedBoxDate = new Date('2024-01-01');
    requestedBoxDate.setMonth(elementId.charAt(0) - 1);
    requestedBoxDate.setDate(elementId.substring(1, 3));
    return compareDatesWithoutHours(currentDate, requestedBoxDate) >= 0;
}

function compareDatesWithoutHours(date1, date2) {
    // years should be compared too :)
    if (date1.getMonth() >= date2.getMonth() && date1.getDate() > date2.getDate()) {
        return 1;
    } else if (date1.getMonth() === date2.getMonth() && date1.getDate() === date2.getDate()) {
        return 0;
    } else {
        return -1;
    }
}

function fadeOut(elementId) {
    const element = document.getElementById(elementId);
    let opacity = 1;
    let intervalId = setInterval(function () {
        if (opacity > 0) {
            opacity = opacity - 0.1;
            element.style.opacity = opacity;
        } else {
            clearInterval(intervalId);
            element.style.display = 'none';
        }
    }, 50);
}

function createCookie() {
    const rows = document.getElementById('calendar-table').rows;
    for (let row of rows) {
        for (let cell of row.cells) {
            const coverElement = cell.getElementsByClassName('cell-cover')[0];
            if (coverElement !== undefined) {
                document.cookie = coverElement.id + '=0';
            }
        }
    }
}

function updateCookie(elementId, value) {
    let cookies = document.cookie;
    if (value === 0) {
        cookies = cookies.replace(elementId + '=1', elementId + '=0');
    } else if (value === 1) {
        cookies = cookies.replace(elementId + '=0', elementId + '=1');
    }
    document.cookie = cookies;
}

function loadRevealedCovers() {
    const cookiesArray = document.cookie.split(';');
    for (let cookie of cookiesArray) {
        if (cookie.search('=1') > 0) {
            const elementId = cookie.split('=')[0];
            fadeOut(elementId.replaceAll(' ', ''));
        }
    }
}

function init() {
    if (document.cookie.length > 0) {
        loadRevealedCovers();
    } else {
        createCookie();
    }
}

window.onload = init;
