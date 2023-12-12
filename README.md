# DEZSYS_GK72_WAREHOUSE_MOM

## Fragestellungen

- Nennen Sie mindestens 4 Eigenschaften der Message Oriented Middleware?
- Was versteht man unter einer transienten und synchronen Kommunikation?
- Beschreiben Sie die Funktionsweise einer JMS Queue?
- JMS Overview - Beschreiben Sie die wichtigsten JMS Klassen und deren Zusammenhang?
- Beschreiben Sie die Funktionsweise eines JMS Topic?
- Was versteht man unter einem lose gekoppelten verteilten System? Nennen Sie ein Beispiel dazu. Warum spricht man hier von lose?

## Funktionsweise des Programs

Das Programm funktioniert wie folgt: Jeder der Lagerstandorte legt ein eigenes Topic an, zu welchem die Daten dann gesendet werden. Die Zentrale holt sich dann die Daten der Lagerstandorte, in dem er einfach die Topic einzeln ausliest. Woher weiss die Zentrale, welche Topics existieren? Fuer dieses Problem gibt es eine extra Topic mit dem Namen "registrations". Diese verwaltet jegliche Lagerstandorte, welche Daten kommunizeren. Die Zentrale hat fuer die Verwaltung der Registrations eine eigene Klasse, welche eine Zentrale Map nach dem Singleton-Pattern verwaltet, und von dieser dann einzelne Receiver speichert. Somit besteht das Program aus 2 Kernkomponenten: Topics der Langerstandorte (verwaltet in einer Map in Registrations) und Registrations, welche dann jedes mal geupdatet wird.

### Zentrale


### Warehouses

- Warehouses werden nicht immer als extra Prozess unter einem eigenen Port gestartet; stattdessen werden sie durch ihre URL unterschieden. Warehouses sind unter http://localhost:8081/warehouse/\{id\}/sendData erreichbar, wobei \{id\} die Number des Warehouses entspricht. Da dies zu Simulationszwecken implementiert ist, werden Warehouses als unabhaengige Entitaeten betrachtet und es wird keinerlei Informationen bei der Standort-Applikation zu den Warehouses gespeichert. Daten der Warehouses werden jedes mal zufaellig genertiert, Code hierfuer wurde von der letzten Aufgabe uebernommen.
  Code fuer den Sender
```java
package tgm.aahuja.warehouse.controller;

import java.io.Serializable;
import javax.jms.Connection;
import javax.jms.ConnectionFactory;
import javax.jms.DeliveryMode;
import javax.jms.Destination;
import javax.jms.JMSException;
import javax.jms.MessageProducer;
import javax.jms.ObjectMessage;
import javax.jms.Session;
import org.apache.activemq.ActiveMQConnection;
import org.apache.activemq.ActiveMQConnectionFactory;

public class WarehouseSender {

  private static String user = ActiveMQConnection.DEFAULT_USER;
  private static String password = ActiveMQConnection.DEFAULT_PASSWORD;
  private static String url = ActiveMQConnection.DEFAULT_BROKER_URL;

  Session session = null;
  Connection connection = null;
  MessageProducer producer = null;
  Destination destination = null;

  public WarehouseSender(String subject) {

    System.out.println("Sender started.");

    // Create the connection.
    try {

      ConnectionFactory connectionFactory =
      new ActiveMQConnectionFactory(user, password, url);
      connection = connectionFactory.createConnection();
      connection.start();

      // Create the session
      session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
      destination = session.createTopic(subject);

      // Create the producer.
      producer = session.createProducer(destination);
      producer.setDeliveryMode(DeliveryMode.NON_PERSISTENT);

      // Create the message
    } catch (Exception e) {
      System.out.println("[MessageProducer] Caught: " + e);
      e.printStackTrace();
    }
    System.out.println("Sender finished.");
  }

  public void stop() {
    try {
      producer.close();
      session.close();
      connection.close();
    } catch (Exception e) {
      System.err.println("[Failed while closing] " + e);
    }
  }

  public void sendMessage(Serializable obj) {
    try {

      ObjectMessage message = session.createObjectMessage(obj);
      producer.send(message);
    } catch (JMSException e) {
      e.printStackTrace();
    }
  }
}
```
  Code fuer den Controller, sendData
```java
@RequestMapping("/warehouse/{inID}/sendData")
public String sendData(@PathVariable String inID) {
registration.sendMessage("Warehouse" + inID);
  WarehouseSender sender = new WarehouseSender("Warehouse" + inID);
  sender.sendMessage(service.getWarehouseData(inID));
  sender.stop();
  return "Data sent.";
}
```

### Zentrale

Der allgemeien Funktionsweise der Zentrale wurde bereits beschrieben, eine weitere relevante Information ist, dass jegliche Acknowledgements ueber eine weitere Topic mit dem Namen "acknowledgements". Durch die Funktionsweise des Topic koennten sich die Lagerstandorte als Receiver einschreiben, und so die Strings in der Topic parsen.
```java
package tgm.aahuja.warehouse.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Set;
import javax.jms.Connection;
import javax.jms.Destination;
import javax.jms.JMSException;
import javax.jms.MessageConsumer;
import javax.jms.ObjectMessage;
import javax.jms.Session;
import org.apache.activemq.ActiveMQConnection;
import org.apache.activemq.ActiveMQConnectionFactory;

public class Registration {
  private static HashMap<String, WarehouseReceiver> clients = new HashMap<>();
  private static Registration instance = new Registration();

  private Session session = null;
  private Connection connection = null;
  private MessageConsumer consumer = null;
  private Destination destination = null;

  private Registration() {
    System.out.println("Receiver started.");

    try {
      ActiveMQConnectionFactory connectionFactory =
      new ActiveMQConnectionFactory(ActiveMQConnection.DEFAULT_USER,
        ActiveMQConnection.DEFAULT_PASSWORD,
        ActiveMQConnection.DEFAULT_BROKER_URL);
      connectionFactory.setTrustedPackages(
        List.of("tgm.aahuja.warehouse.model", "java.util"));
      connection = connectionFactory.createConnection();
      connection.start();
      session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
      destination = session.createTopic("registration");
      consumer = session.createConsumer(destination);
    } catch (Exception e) {
      System.out.println("[Initialization Registration] Caught: " + e);
      stop();
    }
  }

  public static void stop() {
    try {
      instance.consumer.close();
      instance.session.close();
      instance.connection.close();
    } catch (Exception e) {
      System.err.println(e);
      return;
    }
  }

  public static WarehouseReceiver get(String key) { return clients.get(key); }

  public static Set<String> keys() { return clients.keySet(); }

  public static void updateRegistrations() {
    try {
      ObjectMessage message = (ObjectMessage)instance.consumer.receiveNoWait();
      while (message != null) {
        String value = (String) message.getObject();
        System.out.println("Message received: " + value);
        message.acknowledge();
        if (clients.get(value) == null)
        clients.put(value, new WarehouseReceiver(value));
        message = (ObjectMessage)instance.consumer.receiveNoWait();
      }
    } catch (JMSException e) {
      System.err.println(e);
      return;
    }
  }
}
```
```java
@RequestMapping(value = "/zentrale/data",
  produces = MediaType.APPLICATION_JSON_VALUE)
public HashMap<String, ArrayList<WarehouseData>>
warehouseData() {
  HashMap<String, ArrayList<WarehouseData>> data = new HashMap<>();
  System.out.println("Update Registration");
  Registration.updateRegistrations();
  HashSet<String> keys = new HashSet<String>(Registration.keys());
  for (String key : keys) {
    data.put(key, Registration.get(key).getMessage());
  }
  return data;
}
```
