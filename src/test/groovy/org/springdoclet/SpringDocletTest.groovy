package org.springdoclet

import com.sun.tools.javadoc.Main
import org.junit.Test

class SpringDocletTest {
  @Test
  public void docletInvoked() {
    def realStdout = System.out
    def buf = new ByteArrayOutputStream()
    System.out = new PrintStream(buf)

    def args = ["-sourcepath", "./sample",
            "-subpackages", "org.springframework.samples.petclinic"] as String[]
    Main.execute "docletTest", SpringDoclet.class.name, args

    def output = buf.toString()

    System.out = realStdout
    println "Output=[$output]"

    assertComponents(output)
    assertMappings(output)
  }

  private def assertMappings(String output) {
    assert output.contains('RequestMappings:')
    assert output.contains('GET "/owners/new"')
    assert output.contains('POST "/owners/new"')

    assert output.contains('GET "/owners/{ownerId}/pets/new"')
    assert output.contains('POST "/owners/{ownerId}/pets/new"')

    assert output.contains('GET "/owners/*/pets/{petId}/visits/new"')
    assert output.contains('POST "/owners/*/pets/{petId}/visits/new"')

    assert output.contains('GET "/"')
    assert output.contains('GET "/vets"')
    assert output.contains('GET "/owners/{ownerId}"')
    assert output.contains('GET "/owners/*/pets/{petId}/visits"')

    assert output.contains('GET "/owners/{ownerId}/edit"')
    assert output.contains('PUT "/owners/{ownerId}/edit"')

    assert output.contains('GET "/owners/*/pets/{petId}/edit"')
    assert output.contains('PUT "/owners/*/pets/{petId}/edit"')
    assert output.contains('POST "/owners/*/pets/{petId}/edit"')
    assert output.contains('DELETE "/owners/*/pets/{petId}/edit"')

    assert output.contains('GET "/owners/search"')
    assert output.contains('GET "/owners"')
  }

  private def assertComponents(String output) {
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