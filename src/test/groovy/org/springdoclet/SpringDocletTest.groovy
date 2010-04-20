package org.springdoclet

import com.sun.tools.javadoc.Main
import org.junit.Test

class SpringDocletTest {
  @Test
  public void docletInvoked() {
    File file = new File("./sample", "spring-summary.html")
    file.delete()
    assert !file.exists()

    def args = ["-sourcepath", "./sample/src/main/java",
            "-subpackages", "org.springframework.samples.petclinic",
            "-d", "./sample"] as String[]
    Main.execute "docletTest", SpringDoclet.class.name, args

    assert file.exists()

    def output = file.readLines()

    println "Output=[${output}]"

    assertMappings(output)
    assertComponents(output)
  }

  private def assertMappings(output) {
    assert output.contains('RequestMappings:')
    
    assert output.contains('GET "/owners/new": org.springframework.samples.petclinic.web.AddOwnerForm')
    assert output.contains('POST "/owners/new": org.springframework.samples.petclinic.web.AddOwnerForm')

    assert output.contains('GET "/owners/{ownerId}/pets/new": org.springframework.samples.petclinic.web.AddPetForm')
    assert output.contains('POST "/owners/{ownerId}/pets/new": org.springframework.samples.petclinic.web.AddPetForm')

    assert output.contains('GET "/owners/*/pets/{petId}/visits/new": org.springframework.samples.petclinic.web.AddVisitForm')
    assert output.contains('POST "/owners/*/pets/{petId}/visits/new": org.springframework.samples.petclinic.web.AddVisitForm')

    assert output.contains('GET "/": org.springframework.samples.petclinic.web.ClinicController')
    assert output.contains('GET "/vets": org.springframework.samples.petclinic.web.ClinicController')
    assert output.contains('GET "/owners/{ownerId}": org.springframework.samples.petclinic.web.ClinicController')
    assert output.contains('GET "/owners/*/pets/{petId}/visits": org.springframework.samples.petclinic.web.ClinicController')

    assert output.contains('GET "/owners/{ownerId}/edit": org.springframework.samples.petclinic.web.EditOwnerForm')
    assert output.contains('PUT "/owners/{ownerId}/edit": org.springframework.samples.petclinic.web.EditOwnerForm')

    assert output.contains('GET "/owners/*/pets/{petId}/edit": org.springframework.samples.petclinic.web.EditPetForm')
    assert output.contains('PUT "/owners/*/pets/{petId}/edit": org.springframework.samples.petclinic.web.EditPetForm')
    assert output.contains('POST "/owners/*/pets/{petId}/edit": org.springframework.samples.petclinic.web.EditPetForm')
    assert output.contains('DELETE "/owners/*/pets/{petId}/edit": org.springframework.samples.petclinic.web.EditPetForm')

    assert output.contains('GET "/owners/search": org.springframework.samples.petclinic.web.FindOwnersForm')
    assert output.contains('GET "/owners": org.springframework.samples.petclinic.web.FindOwnersForm')
  }

  private def assertComponents(output) {
    assert output.contains('Components:')

    assert output.contains('Controller: org.springframework.samples.petclinic.web.FindOwnersForm')
    assert output.contains('Controller: org.springframework.samples.petclinic.web.EditPetForm')
    assert output.contains('Controller: org.springframework.samples.petclinic.web.EditOwnerForm')
    assert output.contains('Controller: org.springframework.samples.petclinic.web.ClinicController')
    assert output.contains('Controller: org.springframework.samples.petclinic.web.AddVisitForm')
    assert output.contains('Controller: org.springframework.samples.petclinic.web.AddPetForm')
    assert output.contains('Controller: org.springframework.samples.petclinic.web.AddOwnerForm')

    assert output.contains('Repository: org.springframework.samples.petclinic.hibernate.HibernateClinic')
    assert output.contains('Repository: org.springframework.samples.petclinic.jpa.EntityManagerClinic')

    assert output.contains('Service: org.springframework.samples.petclinic.jdbc.SimpleJdbcClinic')
  }
}