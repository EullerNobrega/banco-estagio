package br.unicap.bancoestagio.consumer;

import org.eclipse.microprofile.reactive.messaging.Incoming;
import org.jboss.logging.Logger;

import br.unicap.bancoestagio.model.Student;

public class StudentConsumer {

    private static final Logger LOGGER = Logger.getLogger("StudentConsumer");

    @Incoming("students-from-kafka")
    public void receive (Student student) {
        LOGGER.infof("Received student: %s", student.getName());
    }
}
