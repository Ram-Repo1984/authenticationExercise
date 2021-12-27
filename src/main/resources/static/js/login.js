
$(document).ready(function() {
// on ready
});


async function iniciarSesion(){

let datos = {};
datos.email = document.getElementById('txtEmail').value;
datos.password = document.getElementById('txtPassword').value;

 const request = await fetch('api/login', {
    method: 'POST',
    headers: {
      'Accept': 'application/json',
      'Content-Type': 'application/json'
    },
    body: JSON.stringify(datos)  /// esto convierte cualquier metodo de javascript a Json
  });

  /// esta request, debe mandar un error 401, falla de credenciales en el servidor

  const respuesta = await request.text();

  if(respuesta != 'Fail'){
  localStorage.token = respuesta;
  localStorage.email = datos.email;
  window.location.href = 'usuarios.html'
  }else{
  alert("Credenciales incorrectas");
  }
}