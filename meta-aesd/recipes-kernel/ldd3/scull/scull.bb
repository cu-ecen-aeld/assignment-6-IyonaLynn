# See https://git.yoctoproject.org/poky/tree/meta/files/common-licenses
LICENSE = "MIT"
LIC_FILES_CHKSUM = "file://${COMMON_LICENSE_DIR}/MIT;md5=0835ade698e0bcf8506ecda2f7b4f302"

# TODO: Set this with the path to your assignments repo. Use ssh protocol
SRC_URI = "git://git@github.com/cu-ecen-aeld/assignment-7-IyonaLynn.git;protocol=ssh;branch=main"
SRC_URI += "file://scull-init"

PV = "1.0+git${SRCPV}"
# TODO: Set to reference a specific commit hash in your assignment repo
SRCREV = "edfa61fa7a87bc9f3006810556b6afd6746eb055"

# Set staging directory
S = "${WORKDIR}/git/scull"

# Kernel module compilation
inherit module
inherit update-rc.d

INITSCRIPT_NAME = "scull"
INITSCRIPT_PARAMS = "defaults 90"

do_configure () {
    :
}

do_compile () {
    oe_runmake -C ${STAGING_KERNEL_DIR} M=${S} modules
}

do_install () {
    oe_runmake -C ${STAGING_KERNEL_DIR} M=${S} modules_install INSTALL_MOD_PATH=${D}

    # Ensure the directory for kernel modules exists
    install -d ${D}/lib/modules/${KERNEL_VERSION}/extra/
}

do_install:append() {
    install -d ${D}${sysconfdir}/init.d
    install -m 0755 ${WORKDIR}/files/scull-init ${D}${sysconfdir}/init.d/scull
}


# Define installed files
FILES:${PN} += "/lib/modules/${KERNEL_VERSION}/extra/scull.ko"
FILES:${PN} += "${sysconfdir}/init.d/scull-init"

RPROVIDES:${PN} += "kernel-module-scull-${KERNEL_VERSION}"

