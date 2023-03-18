package ejercicios;

import java.util.Arrays;

import org.bson.Document;

import com.mongodb.MongoClient;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;

public class EjercicioDos {
	// Crear una conexión a MongoDB.
	private static MongoClient mongoClient = new MongoClient("127.0.0.1", 27017);
	// Obtener una base de datos llamada "media".
	private static MongoDatabase database = mongoClient.getDatabase("ej2RecuperacionMongoDB");

	public static void main(String[] args) {
		// Obtener las colecciones correspondientes
		// Llamamos a los métodos que vayamos a poner a prueba
		//        ejercicioUno(mediaCollection);
		//        ejercicioDos(mediaCollection);
		//        ejercicioTres(mediaCollection);
		//        ejercicioCuatro();
		ejercicioCinco();

		// Terminar la conexión despues de realizar cada método.
		mongoClient.close();
	}

	/**
	 * 1) Insertar los documentos dados en una base de datos llamada «media» en una única operación.
	 * @param mediaCollection
	 */
	public static void ejercicioUno() {
		MongoCollection<Document> mediaCollection = database.getCollection("media");
		// Crear los documentos correspondientes para cada uno de los datos proporcionados
		Document libros = new Document("tipo", "libro")
				.append("titulo", "Java para todos")
				.append("ISBN", "987-1-2344-5334-8")
				.append("editorial", "Anaya")
				.append("Autor", Arrays.asList("Pepe Caballero", "Isabel Sanz", "Timoteo Marino"))
				.append("capítulos", Arrays.asList(
						new Document("capitulo", 1).append("titulo", "Primeros pasos en Java").append("longitud", 20),
						new Document("capitulo", 2).append("titulo", "Primeros pasos en Java").append("longitud", 25)
						));
		Document cds = new Document("tipo", "CD")
				.append("Artista", "Los piratas")
				.append("Titulo", "Recuerdos")
				.append("canciones", Arrays.asList(
						new Document("cancion", 1).append("titulo", "Adiós mi barco").append("longitud", "3:20"),
						new Document("cancion", 2).append("titulo", "Pajaritos").append("longitud", "4:15")
						));
		Document dvds = new Document("tipo", "DVD")
				.append("Titulo", "Matrix")
				.append("estreno", 1999)
				.append("actores", Arrays.asList(
						"Keanu Reeves",
						"Carry-Anne Moss",
						"Laurence Fishburne",
						"Hugo Weaving",
						"Gloria Foster",
						"Joe Pantoliano"
						));
		System.out.println("Los datos se han insertado correctamente en las colecciones dentro de la BD=" + database.getName());

		// Insertar los documentos en sus respectivas colecciones
		mediaCollection.insertOne(libros);
		mediaCollection.insertOne(cds);
		mediaCollection.insertOne(dvds);
	}

	/**
	 * 2) Del documento que hace referencia a la película «Matrix» recuperar el array de actores.
	 */
	public static void ejercicioDos() {
		MongoCollection<Document> mediaCollection = database.getCollection("media");
		Document filtro = new Document("Titulo", "Matrix");
		Document proyeccion = new Document("actores", 1).append("_id", 0);
		Document resultado = mediaCollection.find(filtro).projection(proyeccion).first();

		System.out.println(resultado);
	}

	/**
	 * 3) Del documento que hace referencia a la película «Matrix» recuperar todos los
	 * campos de información excepto el array de actores.
	 */
	public static void ejercicioTres() {
		MongoCollection<Document> mediaCollection = database.getCollection("media");
		// Definir el filtro para buscar la película "Matrix" por su título
		Document filtro = new Document("Titulo", "Matrix");
		// Definir la proyección para excluir el array de actores
		Document proyeccion = new Document("actores", 0);
		// Buscar el documento correspondiente a "Matrix" con la proyección definida
		Document resultado = mediaCollection.find(filtro).projection(proyeccion).first();
		// Imprimir el resultado por consola
		System.out.println(resultado.toJson());
	}

	/**
	 * 4) Del documento que hace referencia a la película «Matrix» recuperar un único
	 * documento en el que aparezcan solo los campos tipo y título.
	 */
	public static void ejercicioCuatro() {
		MongoCollection<Document> mediaCollection = database.getCollection("media");
		Document query = new Document("Titulo", "Matrix");
		Document projection = new Document("tipo", 1)
				.append("Titulo", 1)
				.append("_id", 0);
		Document result = mediaCollection.find(query).projection(projection).first();
		System.out.println(result.toJson());
	}

	/**
	 * 5) Recuperar todos los documentos que sean de tipo «libro» y 
	 * editorial «Anaya» mostrando solo el array «capítulos».
	 */
	public static void ejercicioCinco() {
		MongoCollection<Document> mediaCollection = database.getCollection("media");
		// Realizar la consulta
		Document query = new Document("tipo", "libro").append("editorial", "Anaya");
		Document projection = new Document("capitulos", 1).append("_id", 0);
		mediaCollection.find(query).projection(projection).forEach((Document document) -> {
			System.out.println(document.toJson());
		});
	}

}
