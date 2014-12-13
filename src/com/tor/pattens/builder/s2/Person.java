package com.tor.pattens.builder.s2;


import java.util.Calendar;

/**
 * User: tor
 * Date: 02.12.14
 * Time: 21:11
 * <p>
 * Смысл паттерна «билдер» в том, чтобы состояние необходимое для конструкции объекта вынести в отдельный объект
 * билдера, который не будет использоваться в реальной работе. Это так же позволяет вынести какую-то логику
 * конструирования
 * <p>
 * Задач buider решает много, лично я создаю его для следующих целей
 <li>На вход конструктору подается большое количество объектов, порядок которых легко перепутать
 <li>Я хочу сделать все поля класса неизменяемыми, чтобы немного облегчить себе жизнь в многопоточный среде (в идеале это все поля final)
 <li>В конструкторе происходят какие то действия, которые могут вызвать исключения ( в builder это вынесется в set метод поля) — например на вход подается json и не факт что там есть нужное поле, да и вообще парсить его в конструкторе я считаю некрасиво
 <li>Еще можно придумать случай когда внутри конструктора меняются стратегиии инициализации полей, тогда можно это вынести в set методы builder'а и еще задавать стратегии
 */
public class Person {

    private final String name;
    private final int age;

    public Person(String name, int age) {
        this.name = name;
        this.age = age;
    }

    public String getName() {
        return name;
    }

    public int getAge() {
        return age;
    }

    public static Builder builder() {
        return new Builder();
    }

    public static class Builder {
        private NameGenerator nameGenerator = new RandomNameGenerator();
        private Integer age;

        private Builder age(int age) {
            this.age = age;
            return this;
        }

        public Builder birthDate(Calendar date) {
            return age(Calendar.getInstance().get(Calendar.YEAR) - date.get(Calendar.YEAR));
        }

        public Builder name(String name) {
            return nameGenerator(new FixedNameGenerator(name));
        }

        public Builder nameGenerator(NameGenerator generator) {
            this.nameGenerator = generator;
            return this;
        }

        public Person build() {
            if (age == null) {
                throw new IllegalArgumentException();
            }

            return new Person(nameGenerator.generate(), age);
        }
    }

    public static void main(String[] args) {
        Person person = Person.builder().age(10).build();
    }
}