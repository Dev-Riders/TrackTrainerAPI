package dev.devriders.tracktrainerrestapiv2.EmailConfigs;

import dev.devriders.tracktrainerrestapiv2.models.AdministradorModel;
import jakarta.mail.internet.MimeMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Component;

@Component
public class EmailsenderAdmin {
    private final JavaMailSender mailSender;
    @Autowired
    public EmailsenderAdmin(JavaMailSender mailSender) {
        this.mailSender = mailSender;
    }
    public void sendPasswordResetEmailAdmin(AdministradorModel administrador) {
        MimeMessage message = mailSender.createMimeMessage();
        try {
            MimeMessageHelper helper = new MimeMessageHelper(message, true);
            helper.setTo(administrador.getCorreo());
            helper.setSubject("Restablecimiento de contraseña");

            String htmlMsg = "<h3>¡Hola, " + administrador.getNombre() + " " + administrador.getApellido() + "!</h3>"
                    + "<p>Hemos recibido una solicitud para restablecer tu contraseña en Track Trainer.</p>"
                    + "<p>Por favor, utiliza el siguiente código para restablecer tu contraseña:</p>"
                    + "<h2>" + administrador.getResetCode() + "</h2>"
                    + "<p>Si tú no solicitaste el restablecimiento de tu contraseña, por favor ignora este correo electrónico o avísanos inmediatamente.</p>"
                    + "<p>Gracias,</p>"
                    + "<p>Equipo de Track Trainer</p>";

            helper.setText(htmlMsg, true);
            mailSender.send(message);
        } catch (jakarta.mail.MessagingException e) {
            throw new RuntimeException(e);
        }
    }
}
