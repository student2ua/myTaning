http://middlewaremagic.com/jboss/?p=247
https://habr.com/ru/post/184460/
http://java-online.ru/javax-jms.xhtml
https://www.webjars.org/

Настройка сервера приложений Wildfly

Чтобы использовать Wildfly для обмена сообщениями JMS необходимо настроить его файл конфигурации {WILDFLY_INSTALL_DIR}/standalone/configuration/standalone.xml. По умолчанию настройки JMS не включены в конфигурационный файл и их нужно определить вручную. Но можно использовать файл конфигурации standalone-full.xml, в котором сервер включает настройки JMS провайдера HornetQ, обеспечивающего создание соответствующих очередей и обмен сообщениями.

В файл конфигурации standalone-full.xml в раздел <hornetq-server> секции <subsystem xmlns="urn:jboss:domain:messaging:2.0"> добавим очередь. Две очереди (ExpiryQueue и DLQ) уже имеются в подразделе <jms-destinations>. Добавим свою очередь testQueue с JNDI 'jms/queue/test' :
<subsystem xmlns="urn:jboss:domain:messaging:2.0">
    <hornetq-server>
    ...
        <jms-destinations>
            <jms-queue name="ExpiryQueue">
                <entry name="java:/jms/queue/ExpiryQueue"/>
            </jms-queue>
            <jms-queue name="DLQ">
                <entry name="java:/jms/queue/DLQ"/>
            </jms-queue>
            <jms-queue name="testQueue">
                <entry name="jms/queue/test"/>
                <entry name="java:jboss/exported/jms/queue/test"/>
            </jms-queue>
        </jms-destinations>
    </hornetq-server>
</subsystem>

Для примера достаточно было добавить один элемент '<entry name="jms/queue/test"/>', который работает внутри контейнера. Второй элемент "java:jboss/exported/jms/queue/test" может работать за пределами контейнера, т.е. из другой JVM. Для него обязательным условием является определение в начале наименования "java:jboss/exported/". Можно было бы конечно использовать и существующие очереди (ExpiryQueue и DLQ).

Для запуска сервера приложений Wildfly не из IDE Eclipse c конфигурационным файлом standalone-full.xml можно использовать командный файл, в котором определить файл в качестве параметра : ./standalone.sh -c standalone-full.xml