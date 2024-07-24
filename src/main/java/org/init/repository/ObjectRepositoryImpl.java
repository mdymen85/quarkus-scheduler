package org.init.repository;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.persistence.EntityManager;
import jakarta.persistence.LockModeType;
import java.util.List;
import org.hibernate.LockOptions;

@ApplicationScoped
public class ObjectRepositoryImpl implements ObjectRepository {

    @Inject
    EntityManager entityManager;

    public void process() {

        //aqui vai a query que se deseja realizar na outbox table
        String query = "";

        /*

        List<Object> objects = entityManager.createQuery(query, Object.class) //esse object class seria o objeto de retorno que neste caso
                // seria o objeto java que representa a outbox table
                // a entidade da outbox table mesmo
                .setLockMode(LockModeType.PESSIMISTIC_WRITE) //faz o lock do registro para que ao escalar o serviço, nao exista problema
                // de concorrencia
                .setHint("javax.persistence.lock.timeout", LockOptions.SKIP_LOCKED) // esta eh a sacada
                //faz com que a tabela seja utilizada com fila no sentido de se existem registros
                //lockados, vai e pega os seguintes registros, eh a melhor forma de escalar o serviço
                .getResultList();


         */

        // precisaria fazer algumas coisas com esses objetos, por exemplo:
        // 1 - enviar para a fila SQS
        // 2 - marcar salvar numa lista que foi enviado para a SQS
        // 3 - no final do processo, salvar na base que o objeto foi enviado com sucesso para que nao seja enviado novamente

        // exemplo de consulta de um codigo aleatorio:
        //
        //        return entityManager.createQuery("SELECT b " +
        //                        "FROM BagReproved b " +
        //                        "WHERE b.destiny.destinyStatus = :destinyStatus " +
        //                        "AND b.managementEmail.emailStatus = :emailStatus", BagReproved.class)
        //                .setParameter("destinyStatus", DestinyStatus.NO_CHOICE)
        //                .setParameter("emailStatus", BagReprovedEmailStatus.NOT_SENT)
        //                .setLockMode(LockModeType.PESSIMISTIC_WRITE)
        //                .setHint("javax.persistence.lock.timeout", LockOptions.SKIP_LOCKED)
        //                .getResultList();

    }

}
