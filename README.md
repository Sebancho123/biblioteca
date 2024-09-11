### Proyecto Personal

### **LibraryPDF(Biblioteca)**
##### **mi nombre : sebastian daza**

##### Linkedin : [Linkedin](https://www.linkedin.com/in/sebastian-daza-nieto-98b306303/ "Linkedin")

<p>
Hola, Como estas Hoy un proyecto que lo hize con Java en intellij idea, Spring boot, Security, SpringMVC, Base de datos MySQl que todo lo manejamos ne codigo Java con sus Anottations Bueno el proyecto trata de una biblioteca donde cada usuario puede reservas libros(Los que quiera) tanto como asi como pedirlos prestados para pues leerlos, pero solo tengo el backend con funcionalidades pero le falta en frontend, que estoy trabajando en ello(*No soy fullStack*) o aver si un amigo me ayuda.
</p>

### **Uso y Funcionalidad del proyecto**

- Necesitar un editor como intellij idea, Netbeans o eclipse
- Para las variables de entorno como las credenciales de la bs de datos necesitar crear una nueva base de datos y ponerlas en tu ide ya sea como la url *la tuya* el username y la contraseña de tu bs de datos en las otras variables como : **SS_USER** aqui va tu nombre o como quieras **SS_PASSWORD** aqui tu contraseña la que quieras **SS_ROLE** aqui un rol ya sea ADMIN o USER.
- Ahora en la que dice **PRIVATE_KEY** tendtas que ir a esta web : https://tools.keycdn.com/sha256-online-generator
y lo que hacen es donde dice **Input value** podemos poner lo que queramos and le damos generate y el codigo que nos de lo ponemos en nuesto **PRIVATE_KEY** , 
- Ahora en nuestro **USER_GENERATOR** ponemos nuestro nombre o lo que queramos
- Ahora para el inicio de session con google y github es mas dificil conseguir los id y el client secret si quieres eliminas esas dos partes y en Security config eliminar la linea de oauth2 ok. 
- Si quieres implementarlo para google seria asi.
- **Paso 1** Vas a esta pagina : https://console.cloud.google.com/welcome?hl=es&project=biblioteca-435315 y despues busca donde diga crear nuevo o crear nuevo proyecto
poner el nombre si tienes organizacion la pones si no ps no.
- **Paso 2** luego te aparecera una ventada despegable tienes que ver el nombre de tu proyecto quede con un chulito, despues vas donde el buscador y pones Keys y services y adonde dice Credentials que aparece abajo Apis y services de las.
- **Paso 3** ahora le das donde dice crear credential y das donde menciona el id del cliente de oauth y aparecera 'configurar pantalla de concentimiento' le das.
- **Paso 4** pones nombre de app correo tuyo, un logo *si quieres no lo hagas en realidad* en donde dice domio de app el la priemra casilla pones tu localhost es 8080 empieza por http://y despues tu host
despues saltas a la ultima donde dice correo de desarrollador por el mismo no pasa nada de ay guardar y continuar en la otra ventana igual y en la otra igual y en otra que aparece que dice volver al panel de las.
- **Paso 5** le das en credentials, de hay donde crear credenciales le das lo mismo de arrato el id de hay te ipde tipo de app le das web poner el nombre de la pp y te saltas donde diga URI de redireccionamiento autorizados le das agregar y agregas esto agregas hhtp mas tuhost/login/oauth2/code/google y ya ay te salta el id y el secret los pones en tus variables de entorno y eso seria todo.

### **Bueno y eso es todo!**
