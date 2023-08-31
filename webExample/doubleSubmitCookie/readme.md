# Double Submit Cookie  - ok
This demo Double Submit Cookie Pattern for Preventing [CSRF attacks](https://owasp.org/www-community/attacks/csrf) [[ru](https://learn.javascript.ru/csrf)]
## tutorial
### CSRF. Подделка межсайтовых запросов
#### Как работает CSRF
Для успешной эксплуатации этой уязвимости должны быть выполнены следующие условия:
- Наличие соответствующего действия в приложении, которое злоумышленник хочет заставить выполнить пользователя.
- Обработка сессии на основе Cookie без заголовков, например, authentication.
- Отсутствие токенов, которые валидируются на стороне сервера с каждым запросом, непредсказуемых идентификаторов, 
UID которые сложно предугадать.
#### Рекомендации по устранению CSRF
- Использование CSRF-токенов в заголовке и проверка заголовка на соответствие тому сайту, с которым вы работаете;
- Использование HMAC или метода Signed Double Submit Cookie;
- Использование пользовательского заголовка запроса;
- Использование samesite=strict cookies;
- Внедрение повторной аутентификации, одноразовых токенов или CAPTCHA для получения подтверждения от пользователя.
#### Signed Double Submit Cookie
 Двойная отправка файлов cookie определяется как отправка случайного значения как в файле cookie, так и в качестве 
 параметра запроса, при этом сервер проверяет, равны ли значение файла cookie и значение запроса.
 
 Когда пользователь входит на сайт, создается сеанс, а идентификатор сеанса устанавливается в виде файла cookie в браузере. В то же время для токена CSRF устанавливается еще один файл cookie.
 
 Затем, когда пользователь отправляет безопасную форму, этот токен извлекается из файла cookie и устанавливается как скрытое поле ввода в HTML. Этот файл cookie не может быть установлен как Http Only, так как клиентский сценарий требует доступа к нему, поскольку в этом сценарии конечная точка токена не существует, и на сервере нет записи о сгенерированном токене для этого сеанса.
 
 Сервер проверит токен, отправленный в качестве параметра формы, на соответствие значению файла cookie и разрешит выполнение действия. Злоумышленник из перекрестного источника не может читать какие-либо данные, отправленные с сервера, или изменять значения файлов cookie в соответствии с политикой одного и того же источника.
#### Synchronizer Token Pattern
[Synchronizer Token Pattern](https://medium.com/@kaviru.mihisara/synchronizer-token-pattern-e6b23f53518e)
- Пользователь отправляет запрос GET на сервер
- Сервер устанавливает файл cookie с идентификатором session_id и сохраняет данные сеанса с помощью токена.
- Сервер возвращает HTML с формой, содержащей токен в скрытом поле.
- Пользователь отправляет форму вместе со скрытым полем.
- Сервер сравнивает токен из отправленной формы (скрытое поле) с токеном, сохраненным в хранилище сеанса. Если они совпадают, это означает, что форма отправлена ​​пользователем.

## Link
- [Double Submit Cookie Pattern](https://medium.com/@kaviru.mihisara/double-submit-cookie-pattern-820fc97e51f2)
- [article](http://sanjeewafirst.blogspot.com/2018/09/cross-site-request-forgery-protection_7.html)
- [gitHub](https://github.com/AnuradhaSD/CSRF_DoubleSubmitCookiePattern)
- [Session Management in Java - HttpServlet, Cookies, URL Rewriting](https://www.digitalocean.com/community/tutorials/java-session-management-servlet-httpsession-url-rewriting)
- [GitHub: Anti-CSRF-Part2-DSCP](https://github.com/SkNuwanTissera/Anti-CSRF-Part2-DSCP)

`http://localhost:8081/dsc/login.jsp` login = "testuser" pass = "qwerty";

https://stackoverflow.com/questions/10730362/get-cookie-by-name