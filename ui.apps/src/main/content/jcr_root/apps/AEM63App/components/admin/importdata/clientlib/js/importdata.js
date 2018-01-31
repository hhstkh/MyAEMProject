//Use JQuery AJAX request to post data to a Sling Servlet
$.ajax({
type: 'POST', 
url:'/bin/myImportJob',
data:'id='+ claimId+'&firstName='+ myFirst+'&lastName='+ myLast+'&address='+ address+'&cat='+ cat+'&state='+ state+'&details='+ details+'&date='+ date+'&city='+ city,
success: function(msg){
    alert(msg); //display the data returned by the servlet
}
});