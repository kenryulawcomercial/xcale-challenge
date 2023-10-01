
# Xcale Challenge
# Ruben Cedeño

Esta es la solución propuesta al ejercicio de la creción de un carrito de compras para un e-commerce cualquiera según las indicaciones enviadas, las cuales dejare aqui como referencia:

-------------------------------------------------

The test consist in create a back end application for an e-commerce,
with the following requirements:

● Create a cart (which will hold products). This cart MUST be able to
hold products.

● Cart identifier MUST be generated by your application

● A cart MUST be deleted automatically due to 10 minutes of
inactivity

● Get a cart information given its id.

● Add one or more products to that cart with the following
properties:

    ○ id (numerical)
    ○ description (alphanumerical)
    ○ amount (numerical)

● Delete a cart.

To pass the test, we will take into account the following
considerations:

● Your application meets all the previous requirements

● A minimum test coverage

● Easy to test, check, and deploy

-------------------------------------------------

Esta hecho usando una arquitectura hexagonal con una unica entidad de negocio llamada "Cart", ya que la otra entidad de negocio "Product" funciona solo como un bean simple.

Los endPoints a utilizar son 4:




## API Reference

#### Crear un carrito de compras nuevo, con productos iniciales. Creara un carrito con un ID autogenerado, una fecha de creacion y los productos iniciales

```http
    POST /cart/create
```

curl --location 'http://localhost:8080/cart/create' \
--header 'Accept: application/json' \
--header 'Content-Type: application/json' \
--data '[
    {
        "id": 1,
        "description": "Harina",
        "amount": 2
    },
    {
        "id": 2,
        "description": "Leche",
        "amount": 2
    },
    {
        "id": 3,
        "description": "Huevo",
        "amount": 12
    }
]
'

---------------------------------------------------------

#### Obtener la informacion de un carrito segun su identificador. Obtiene los productos del carrito ademas de que valida internamente si han transcurrido 10 minutos de inactividad para saber si el carrito debe eliminarse o mantenerse vigente

```http
  GET /cart/get/{idCart}
```

curl --location 'http://localhost:8080/cart/get/2' \
--header 'Accept: application/json'

---------------------------------------------------------

#### Agrega productos al carrito. Los productos pueden ser nuevos o repetidos, por lo que se valida la existencia previa del producto para saber si se deben sumar las unidades de dicho producto. Tambien se valida el tiempo de inactividad del carrito antes de la modificacion del mismo

```http
  PUT /cart/put/{idCart}
```

curl --location --request PUT 'http://localhost:8080/cart/put/2' \
--header 'Accept: application/json' \
--header 'Content-Type: application/json' \
--data '[
    {
        "id": 5,
        "description": "Chocolate",
        "amount": 2
    },
    {
        "id": 2,
        "description": "Leche",
        "amount": 1
    }
]'

---------------------------------------------------------

#### Elimina un carrito dado si identificador. Es el metodo mas simple y es una eliminacion directa sin posibilidad de error sea que el identificador exista o no exista

```http
  DELETE /cart/delete/{idCart}
```

curl --location --request DELETE 'http://localhost:8080/cart/delete/1' \
--header 'Accept: application/json'


## Deployment

Para desplegar y probar este proyecto debe contar con: 

.- Entorno de desarrollo Java con JDK 21 y Java al menos en su version 8
.- IDE de Java (en mi caso IntelliJ IDEA)

La solución esta hecha con Springboot, por lo que se levantará un Tomcat embebido que expondra en el puerto 8080 los endPoints descritos anteriormente 



## Running Tests

para correr los Test unitarios, los cuales fueron hecho con Mockito y Junit, solamente se deben ejecutar los comandos de gradle para ejecutarlos, o utilizar el IDE para más comodidad.

```http
    gradle test
```

Los test se hicieron sobre la capa de servicio únicamente, ya que según la separacion de capas en la arquitectura hexagonal, es aqui donde se encuentra la logica del negocio de los carritos de compra


## Authors

- [Ruben Cedeño](https://www.linkedin.com/in/ruben-cedeño-04135962/)

