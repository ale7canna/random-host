package ale7canna.randomhost.application

data class Host(val name: String, val surname: String, val present: Boolean = true) :
    IHost {
    override fun toString(): String =
        "$name $surname: ${if (present) "present" else "absent"}"
}