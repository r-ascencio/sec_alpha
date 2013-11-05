
$('#preguntas-form').stepy({
    description: false,
    legend: false,
    title: false,
    titleClick: false,
    validate: true,
    transition: 'fade',
    block: true,
    back: function(){ return false; },
    titleTarget: '#preguntas-Notitle'
});

// find a better way...
jQuery.extend(jQuery.validator.messages, {
    required: "Campo requerido."
});

$(".stepy-navigator").find('a').addClass("btn").text("Siguiente Candidato > ");

$('.button-back').remove();