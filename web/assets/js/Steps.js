$('#preguntas-form').stepy({
    description: false,
    legend: false,
    title: false,
    titleClick: false,
    validate: true,
    transition: 'fade',
    block: true,
    back: function(){ 
        return false; },
    titleTarget: '#preguntas-Notitle'
});


$('.button-back').remove();