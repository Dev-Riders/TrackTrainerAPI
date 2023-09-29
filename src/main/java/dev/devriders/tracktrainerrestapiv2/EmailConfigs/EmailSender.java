package dev.devriders.tracktrainerrestapiv2.EmailConfigs;

import dev.devriders.tracktrainerrestapiv2.models.UsuarioModel;
import jakarta.mail.internet.MimeMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Component;

@Component
public class EmailSender {

    private final JavaMailSender mailSender;
    @Autowired
    public EmailSender(JavaMailSender mailSender) {
        this.mailSender = mailSender;
    }
    public void sendVerificationEmailUsuario(UsuarioModel usuario) {
        MimeMessage message = mailSender.createMimeMessage();
        try {
            MimeMessageHelper helper = new MimeMessageHelper(message, true);
            helper.setTo(usuario.getCorreo());
            helper.setSubject("Verificación de cuenta");

            String htmlMsg = "<h3>¡Hola, " + usuario.getNombre() + " " + usuario.getApellido() + "!</h3>"
                    + "<p>Gracias por registrarte en Track Trainer. Para completar tu registro, por favor ingresa el siguiente código de verificación:</p>"
                    + "<h2>" + usuario.getVerificationCode() + "</h2>"
                    + "<p>Si tienes alguna pregunta, no dudes en contactarnos. ¡Estamos aquí para ayudarte!</p>"
                    + "<p>Gracias,</p>"
                    + "<p>Equipo de Track Trainer</p>";
            helper.setText(htmlMsg, true);
            mailSender.send(message);
        } catch (jakarta.mail.MessagingException e) {
            throw new RuntimeException(e);
        }
    }
    public void sendPasswordResetEmailUsuario(UsuarioModel usuario) {
        MimeMessage message = mailSender.createMimeMessage();
        try {
            MimeMessageHelper helper = new MimeMessageHelper(message, true);
            helper.setTo(usuario.getCorreo());
            helper.setSubject("Restablecimiento de contraseña");
            String htmlMsg = "<h3>¡Hola, " + usuario.getNombre() + " " + usuario.getApellido() + "!</h3>"
                    + "<p>Hemos recibido una solicitud para restablecer tu contraseña en Track Trainer.</p>"
                    + "<p>Por favor, utiliza el siguiente código para restablecer tu contraseña:</p>"
                    + "<h2>" + usuario.getResetCode() + "</h2>"
                    + "<p>Si tú no solicitaste el restablecimiento de tu contraseña, por favor ignora este correo electrónico o avísanos inmediatamente.</p>"
                    + "<p>Gracias,</p>"
                    + "<p>Equipo de Track Trainer</p>";

            helper.setText(htmlMsg, true);
            /* En caso de que queramos agregar alguna imagen
            FileSystemResource res = new FileSystemResource(new File("src/main/resources/static/image/Logo.png"));
            helper.addInline("logo", res);
            */
            mailSender.send(message);
        } catch (jakarta.mail.MessagingException e) {
            throw new RuntimeException(e);
        }
    }
}

