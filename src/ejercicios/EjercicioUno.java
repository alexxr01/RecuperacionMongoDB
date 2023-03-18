package ejercicios;

import com.mongodb.MongoClient;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.model.Updates;

import org.bson.Document;

import java.util.Arrays;

public class EjercicioUno {
	// Crear una conexión a MongoDB.
	private static MongoClient mongoClient = new MongoClient("127.0.0.1", 27017);
	// Obtener una base de datos llamada "media".
	private static MongoDatabase database = mongoClient.getDatabase("ej1RecuperacionMongoDB");

	public static void main(String[] args) {
		// Obtener las colecciones correspondientes
		MongoCollection<Document> mediaCollection = database.getCollection("media");

		//        ejercicioUno(mediaCollection);
		//        ejercicioDos();
		//        ejercicioTres();
		//        ejercicioCuatro(mediaCollection);
		//        ejercicioCinco(mediaCollection);
		//        ejercicioSeis(mediaCollection);
		//        ejercicioSiete(mediaCollection);
		//        ejercicioOcho(mediaCollection);
		//        ejercicioNueve();
		ejercicioDiez();

		// Cerrar la conexión a MongoDB
		mongoClient.close();
	}
	/**
	 * Ejercicio 1, donde se crea la base de datos, las colecciones y se insertan registros en ellas.
	 * @param librosCollection
	 * @param cdsCollection
	 * @param dvdsCollection
	 */
	public static void ejercicioUno(MongoCollection<Document> mediaCollection) {
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
	 * Ejercicio 2, donde actualizamos una película, en concreto la de Matrix.
	 */
	public static void ejercicioDos() {
		// Obtener la base de datos y la colección correspondiente
		MongoCollection<Document> mediaCollection = database.getCollection("media");
		// Crear el filtro para encontrar el documento correspondiente
		Document filtro = new Document("Titulo", "Matrix");
		// Crear la operación de actualización para establecer el campo "genero"
		Document actualizacion = new Document("$set", new Document("genero", "accion"));
		// Actualizar el documento correspondiente
		mediaCollection.updateOne(filtro, actualizacion);
	}

	public static void ejercicioTres() {
		// Obtener la colección correspondiente
		MongoCollection<Document> mediaCollection = database.getCollection("media");

		// Crear el nuevo documento
		Document nuevoLibroDocument = new Document("tipo", "Libro")
				.append("Titulo", "Constantinopla")
				.append("capitulos", 12)
				.append("leidos", 3);

		// Insertar el nuevo documento en la colección correspondiente
		mediaCollection.insertOne(nuevoLibroDocument);

		// Actualizamos el valor a 5, de manera que saldrá el valor 5, aunque antes se haya intertado 3,
		// el resultado final será 5, es decir, eso sucede porque lo he añadido todo dentro del mismo método.
		Document filtroDocument = new Document("Titulo", "Constantinopla");
		Document updateDocument = new Document("$inc", new Document("leidos", 5));

		mediaCollection.findOneAndUpdate(filtroDocument, updateDocument);
	}

	public static void ejercicioCuatro() {
		MongoCollection<Document> mediaCollection = database.getCollection("media");
		// Crear el filtro y la actualización correspondientes
		Document filtroDocument = new Document("Titulo", "Matrix");
		Document updateDocument = new Document("$set", new Document("genero", "ciencia ficción"));

		// Actualizar el documento en la colección correspondiente
		mediaCollection.updateOne(filtroDocument, updateDocument);
	}

	public static void ejercicioCinco() {
		MongoCollection<Document> mediaCollection = database.getCollection("media");
		// Crear el filtro y la actualización correspondientes
		Document filtroDocument = new Document("titulo", "Java para todos");
		Document updateDocument = new Document("$unset", new Document("editorial", ""));

		// Actualizar el documento en la colección correspondiente
		mediaCollection.updateOne(filtroDocument, updateDocument);
	}

	public static void ejercicioSeis() {
		MongoCollection<Document> mediaCollection = database.getCollection("media");
		// Crear el filtro y la actualización correspondientes
		Document filtroDocument = new Document("titulo", "Java para todos");
		Document updateDocument = new Document("$push", new Document("Autor", "María Sancho"));

		// Actualizar el documento en la colección correspondiente
		mediaCollection.updateOne(filtroDocument, updateDocument);
	}

	public static void ejercicioSiete() {
		MongoCollection<Document> mediaCollection = database.getCollection("media");
		// Crear el filtro y la actualización correspondientes
		Document filtroDocument = new Document("Titulo", "Matrix");
		Document updateDocument = new Document("$pop", new Document("actores", -1));
		updateDocument = new Document("$pop", new Document("actores", 1));

		// Actualizar el documento en la colección correspondiente
		mediaCollection.updateOne(filtroDocument, updateDocument);
	}

	public static void ejercicioOcho() {
		MongoCollection<Document> mediaCollection = database.getCollection("media");
		// Crear el filtro para encontrar el documento "Recuerdos"
		Document filtro = new Document("Titulo", "Recuerdos").append("tipo", "CD");

		// Crear el documento con la nueva canción
		Document nuevaCancion = new Document("cancion", 5)
				.append("titulo", "El atardecer")
				.append("longitud", "6:50");

		// Actualizar el documento
		mediaCollection.updateOne(filtro, Updates.push("canciones", nuevaCancion));
	}

	public static void ejercicioNueve() {
		// Obtener la colección
		MongoCollection<Document> collection = database.getCollection("media");

		// Crear filtro para encontrar el primer documento
		Document filter = new Document().append("_id", collection.find().first().getObjectId("_id"));

		// Eliminar el documento
		collection.deleteOne(filter);
	}

	public static void ejercicioDiez() {
		MongoCollection<Document> mediaCollection = database.getCollection("media");
		Document filter = new Document("tipo", "CD");
		mediaCollection.deleteMany(filter);
	}

}