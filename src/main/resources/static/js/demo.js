function showAlert() {
    $.ajax({
        type: 'post',
        url: '/stream/stop',
        success: function() {
            alert("Process stopped")
        }
    });
}