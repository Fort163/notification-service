package com.quick.recording.notification.service.service.phone;

import com.quick.recording.gateway.config.error.exeption.QRInternalServerErrorException;
import com.quick.recording.gateway.dto.notification.mail.MailCodeDto;
import com.quick.recording.gateway.dto.notification.mail.MailDto;
import com.quick.recording.gateway.dto.notification.mail.MailResult;
import org.springframework.web.multipart.MultipartFile;

public interface PhoneSender {

    MailResult send(MailDto mail, MultipartFile[] files) throws QRInternalServerErrorException;

    MailResult code(MailCodeDto code) throws QRInternalServerErrorException ;

    Boolean checkCode(MailCodeDto mailCodeDto);

    /*
        @Autowired
    private SmsConfig smsConfig;

    @Autowired
    private CheckSenderSmsRepository checkSenderSmsRepository;

    @Override
    public AnswerDto checkPhone(ClientDto dto){
        CheckSenderSmsEntity entity = sendSMS(dto);
        AnswerDto answerDto = new AnswerDto();
        answerDto.setResult(true);
        answerDto.setMessage("Зарос отправлен введите код из последних 4 цифр номера");
        answerDto.setCode(entity.getId());
        return answerDto;
    }

    @Override
    public void afterCreateService(ServiceEntity service){
        UserEntity userEmployee = service.getEmployee().getUser();
        String phoneEmployee = userEmployee.getPhoneNumber();
        ClientEntity client = service.getClient();
        String phoneClient = client.getPhone();
        String nameClient = client.getName();
        if(phoneEmployee!=null){
            String messageEmployee = getMessageEmployee(service);
            this.sendSMS(phoneEmployee,userEmployee.getFullName(),messageEmployee);
        }
        String messageClient = getMessageClient(service);
        this.sendSMS(phoneClient,nameClient,messageClient);
    }


    private CheckSenderSmsEntity sendSMS(ClientDto dto){
        return this.sendSMS(dto,"code");
    }

    private CheckSenderSmsEntity sendSMS(ClientDto dto, String message){
        return sendSMS(dto.getPhone(),dto.getName(),message);
    }

    private CheckSenderSmsEntity sendSMS(String phone,String name, String message){
        OkHttpClient client = new OkHttpClient();
        Request request = new Request.Builder().url(createUrl(phone,message)).method("GET", null).build();
        try {
            Response response = client.newCall(request).execute();
            if(response.code() == 200){
                String jsonData = response.body().string();
                JSONObject json = new JSONObject(jsonData);
                CheckSenderSmsEntity entity = new CheckSenderSmsEntity();
                if(message.equals("code")){
                    entity.setCode((String) json.get("code"));
                }
                entity.setId(Long.parseLong(((Integer)json.get("id")).toString()));
                entity.setCnt(((Integer)json.get("id")).toString());
                entity.setPhone(phone);
                entity.setName(name);
                CheckSenderSmsEntity save = checkSenderSmsRepository.save(entity);
                return save;
            }
            else {

            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    private String createUrl(String phone, String message){
        String url = smsConfig.getUrl() + "?";
        url+="login="+smsConfig.getLogin()+ "&";
        url+="psw="+smsConfig.getPassword()+ "&";
        url+="phones="+phone+ "&";
        url+="mes="+message+ "&";
        url+="call="+(message.equalsIgnoreCase("code")?1:0)+ "&";
        url+="fmt=3";
        return url;
    }

    private String getMessageClient(ServiceEntity service) {
        EmployeeEntity employee = service.getEmployee();
        String employeeName = employee.getName()+" "+employee.getUser().getFullName();
        String nameCompany = employee.getCompany().getName();
        String serviceTypeName = service.getServiceType().getName();
        String serviceTypeTime = DateTimeUtil.convertLocalTime(service.getServiceType().getWorkClock());
        String dateTime = DateTimeUtil.convertLocalDateTime(service.getTime());
        String message = "Оформлена онлайн запись на "+dateTime;+ " в компанию : "+nameCompany+".\n" +
                "Услуга "+serviceTypeName+" время выполнения : "+serviceTypeTime+".\n" +
                "Ваш специалист - "+employeeName+".\n";
        return message;
}

    private String getMessageEmployee(ServiceEntity service) {
        ClientEntity client = service.getClient();
        String name = service.getEmployee().getUser().getFirstName();
        String clientName = client.getName();
        String clientPhone = client.getPhone();
        String serviceTypeName = service.getServiceType().getName();
        String serviceTypeTime = DateTimeUtil.convertLocalTime(service.getServiceType().getWorkClock());
        String dateTime = DateTimeUtil.convertLocalDateTime(service.getTime());
        String message = name+", к вам оформлена онлайн запись "+dateTime+".";/* +
                "На услугу "+serviceTypeName+" время выполнения : "+serviceTypeTime+".\n" +
                "Клиент : "+clientName+" телефон "+clientPhone+".\n";
        return message;
    }
     */

}
